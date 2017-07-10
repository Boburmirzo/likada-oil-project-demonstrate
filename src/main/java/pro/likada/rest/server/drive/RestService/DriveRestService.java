package pro.likada.rest.server.drive.RestService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.*;
import pro.likada.model.*;
import pro.likada.rest.server.wrapper.drive.*;
import pro.likada.util.FileIOSystemUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bumur on 03.04.2017.
 */
@Path("/getDrives")
@RequestScoped
public class DriveRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriveRestService.class);

    @Inject
    private DriveDAO driveDAO;
    @Inject
    private DriveStateTypeDAO driveStateTypeDAO;
    @Inject
    private RestAuthenticationDAO restAuthenticationDAO;
    @Inject
    private DocumentDAO documentDAO;
    @Inject
    private DocumentTypeDAO documentTypeDAO;

    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
            .entity("You cannot access this resource").build();
    @GET
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response fetchDriveList(@QueryParam("token") String token) throws JsonProcessingException {
        if(token==null)
            return Response.status(Response.Status.NOT_FOUND).entity("Укажите токен: ").build();
        RestAuthentication restAuthentication=restAuthenticationDAO.findByToken(token);
        if(restAuthentication==null)
            return ACCESS_DENIED;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        List<DriveWrapper> driveWrappers = new ArrayList<>();
        List<Drive> drives = driveDAO.getAllDrivesOfTerminalForCar(restAuthentication.getRestUser().getId());
        if(drives!=null && drives.size()>0) {
            for (Drive drive : drives) {
                driveWrappers.add(makeDriveWrapper(drive, null));
            }
        }
        return Response.ok(driveWrappers).build();
    }


    @GET
    @Path("/getById")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response fetchById(@QueryParam("driveId") String id, @QueryParam("token") String token) throws JsonProcessingException {
        if(token==null)
            return Response.status(Response.Status.NOT_FOUND).entity("Укажите токен: ").build();
        if(restAuthenticationDAO.findByToken(token)==null)
            return ACCESS_DENIED;
        LOGGER.info("fetch Drive by id");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        Drive drive = driveDAO.findById(Long.valueOf(id));
        if (drive == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Не найдено рейс: " + id).build();
        }

        //String json=mapper.writeValueAsString(makeDriveWrapper(drive,null));

        return Response.ok(makeDriveWrapper(drive,null)).build();

    }

    private DriveWrapper makeDriveWrapper(Drive drive, Long driveStateTypeId){
        DriveWrapper driveWrapper = new DriveWrapper();
        driveWrapper.setSerial(drive.getDriveSerial());
        if(drive.getDriver()!=null) {
            driveWrapper.setDriver(drive.getDriver().getTitle());
        }
        driveWrapper.setStatus(drive.getDriveStatus().getName());
        driveWrapper.setId(drive.getId().toString());
        driveWrapper.setProduct(drive.getProductName());
        if(drive.getLogistician()!=null) {
            driveWrapper.setLogisticianNameShort(drive.getLogistician().getTitle());
            driveWrapper.setLogisticianNameFull(drive.getLogistician().getLastName() + " " +
                    drive.getLogistician().getFirstName() + " " + drive.getLogistician().getPatronymic());
        }
        if(drive.getVehicle()!=null) {
            driveWrapper.setVehicle(drive.getVehicle().getTruck().getGovNumber());
        }
        driveWrapper.setDescription(drive.getDescription());
        if(drive.getDriveStatesList().size()>0) {
            DriveStateHistoryWrapper driveStateHistoryWrapper = new DriveStateHistoryWrapper();
            DriveState driveStateCurrent=null;
            for(DriveState driveState : drive.getDriveStatesList()){
                if(driveState.isCheckPreviousState()) {
                    driveStateCurrent = driveState;
                    driveStateHistoryWrapper.setCheckContinue(true);
                    break;
                }
            }
            if(driveStateCurrent == null) {
                driveStateCurrent = drive.getDriveStatesList().get(0);
            }
            driveStateHistoryWrapper.setId(driveStateCurrent.getId());
            driveStateHistoryWrapper.setTypeId(driveStateCurrent.getDriveStateType().getId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            driveStateHistoryWrapper.setModifiedDate(simpleDateFormat.format(driveStateCurrent.getModifiedDate()));
            driveStateHistoryWrapper.setName(driveStateCurrent.getDriveStateType().getName());
            driveStateHistoryWrapper.setDescription(driveStateCurrent.getDescription());
//            driveStateHistoryWrapper.setCheckContinue(false);
            driveWrapper.setCurrentState(driveStateHistoryWrapper);
            DriveStateNextWrapper driveStateNextWrapper = new DriveStateNextWrapper();
            DriveStateType driveStateType=null;

            if(!driveStateCurrent.getDriveStateType().getId().equals(new Long(9)))
                driveStateType = driveStateTypeDAO.findById(driveStateCurrent.getDriveStateType().getId() + 1);

            if(driveStateType!=null) {
                driveStateNextWrapper.setTypeId(driveStateType.getId());
                driveStateNextWrapper.setName(driveStateType.getName());
                driveStateNextWrapper.setDescription(driveStateType.getDescription());
                driveWrapper.setNextState(driveStateNextWrapper);
            }
        }
        StopStateWrapper stopStateWrapper = new StopStateWrapper();
        stopStateWrapper.setStopeStateId(new Long(10));
        stopStateWrapper.setStopStateName("Остановка");
        driveWrapper.setStopState(stopStateWrapper);
        SleepStateWrapper sleepStateWrapper = new SleepStateWrapper();
        sleepStateWrapper.setSleepStateId(new Long(12));
        sleepStateWrapper.setSleepStateName("Сон");
        driveWrapper.setSleepState(sleepStateWrapper);
        ContinueStateWrapper continueStateWrapper = new ContinueStateWrapper();
        continueStateWrapper.setContinueStateId(new Long(11));
        continueStateWrapper.setContinueStateName("Продолжить");
        driveWrapper.setContinueState(continueStateWrapper);
        CancelStateWrapper cancelStateWrapper = new CancelStateWrapper();
        cancelStateWrapper.setCancelStateId(new Long(101));
        cancelStateWrapper.setCancelStateName("Отмена");
        driveWrapper.setCancelState(cancelStateWrapper);
        if(drive.getDrivePointsSource().size()>0) {
            List<SourceWrapper> sourceWrappers = new ArrayList<>();
            for (DrivePoint drivePointFrom : drive.getDrivePointsSource()) {
                SourceWrapper sourceWrapper = new SourceWrapper();
                sourceWrapper.setId(drivePointFrom.getId().toString());
                sourceWrapper.setFromWhere(checkPointType(drivePointFrom.getTypeOfObject()));
                sourceWrapper.setAddress(drivePointFrom.getAddress());
                sourceWrapper.setContact(drivePointFrom.getContact());
                sourceWrapper.setCoordinates(drivePointFrom.getCoordinates());
                sourceWrapper.setObject(drivePointFrom.getLinkToObject());
                sourceWrappers.add(sourceWrapper);
            }
            driveWrapper.setSourceWrappers(sourceWrappers.toArray(new SourceWrapper[sourceWrappers.size()]));
        }
        if (drive.getDrivePointsDestination().size()>0) {
            List<DestinationWrapper> destinationWrappers = new ArrayList<>();
            for (DrivePoint drivePointDestination : drive.getDrivePointsDestination()) {
                DestinationWrapper destinationWrapper = new DestinationWrapper();
                destinationWrapper.setId(drivePointDestination.getId().toString());
                destinationWrapper.setFromWhere(checkPointType(drivePointDestination.getTypeOfObject()));
                destinationWrapper.setAddress(drivePointDestination.getAddress());
                destinationWrapper.setContact(drivePointDestination.getContact());
                destinationWrapper.setCoordinates(drivePointDestination.getCoordinates());
                destinationWrapper.setObject(drivePointDestination.getLinkToObject());
                destinationWrappers.add(destinationWrapper);
            }
            driveWrapper.setDestinationWrappers(destinationWrappers.toArray(new DestinationWrapper[destinationWrappers.size()]));
        }
        return driveWrapper;
    }

    private String checkPointType(Integer pointTypeId){
        if(pointTypeId==1) {
            return "База";
        }else if(pointTypeId==2){
            return "Клиент";
        }else {
            return "Другое";
        }
    }

    @GET
    @Path("/stopState")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response fetchStopState(@QueryParam("driveId") String id,@QueryParam("stateTypeId") Long stateTypeId, @QueryParam("token") String token) throws JsonProcessingException {
        if (token == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Укажите токен: ").build();
        if (restAuthenticationDAO.findByToken(token) == null)
            return ACCESS_DENIED;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        if(stateTypeId==101) {
            return Response.ok("Отмена").build();
        }
        Drive drive = driveDAO.findById(Long.valueOf(id));
        if (drive == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Не найдено рейс: " + id).build();

        DriveStateType driveStateType=driveStateTypeDAO.findById(stateTypeId);
        if (driveStateType == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Не найдено состояние: " + id).build();
        }
        if(driveStateType.getId()==10){
            drive.getDriveStatesList().get(0).setCheckPreviousState(true);
            //driveDAO.save(drive);
            saveNewDriveState(drive,driveStateType);
            return Response.ok("Остановка").build();
        }
        if(driveStateType.getId()==12){
            drive.getDriveStatesList().get(0).setCheckPreviousState(true);
            LOGGER.info(drive.getDriveStatesList().get(0).getDriveStateType().getName());
            //driveDAO.save(drive);
            saveNewDriveState(drive,driveStateType);
            return Response.ok("Сон").build();
        }
        if(driveStateType.getId()==11){
            for(DriveState driveState : drive.getDriveStatesList()){
                if(driveState.isCheckPreviousState()) {
                    driveState.setCheckPreviousState(false);
                    saveNewDriveState(drive,driveState.getDriveStateType());
                    break;
                }
            }

            return Response.ok("Продолжение").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Произошла ошибка").build();
    }

    @GET
    @Path("/nextState")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response fetchNextState(@QueryParam("driveId") String id,@QueryParam("stateTypeId") Long stateTypeId, @QueryParam("token") String token) throws JsonProcessingException {
        if(token==null)
            return Response.status(Response.Status.NOT_FOUND).entity("Укажите токен: ").build();
        if(restAuthenticationDAO.findByToken(token)==null)
            return ACCESS_DENIED;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        Drive drive = driveDAO.findById(Long.valueOf(id));
        if (drive == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Не найдено рейс: " + id).build();

        DriveStateType driveStateType=driveStateTypeDAO.findById(stateTypeId);
        if (driveStateType == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Не найдено состояние: " + id).build();
        }
        saveNewDriveState(drive,driveStateType);
        return Response.ok(makeDriveWrapper(driveDAO.findById(Long.valueOf(id)),null)).build();

    }

    @GET
    @Path("/notificationToken")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response fetchNotificationToken(@QueryParam("token") String token, @QueryParam("gcmToken") String gcmToken) throws JsonProcessingException {
        if(token==null)
            return Response.status(Response.Status.NOT_FOUND).entity("Укажите токен: ").build();
        RestAuthentication restAuthentication = restAuthenticationDAO.findByToken(token);
        if(restAuthentication==null)
            return ACCESS_DENIED;
        LOGGER.info(gcmToken);
        if(restAuthentication.getGcmToken()!=null)
            return Response.ok("GCM токен уже есть").build();
        restAuthentication.setGcmToken(gcmToken);
        restAuthenticationDAO.save(restAuthentication);
        return Response.ok("GCM токен получен").build();
    }

    private void saveNewDriveState(Drive drive, DriveStateType driveStateType){
        DriveState newDriveState = new DriveState();
        newDriveState.setDrive(drive);
        newDriveState.setDriveStateType(driveStateType);
        newDriveState.setModifiedDate((new Date(System.currentTimeMillis())));
        drive.getDriveStates().add(newDriveState);
        driveDAO.save(drive);
    }

    @Path("/uploadDriveStateFile")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        LOGGER.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // check if all form parameters are provided
        if (uploadedInputStream == null || fileDetail == null)
            return Response.status(400).entity("Invalid form data").build();
        // create our destination folder, if it not exists
        FileIOSystemUtil fileIOSystemUtil= new FileIOSystemUtil();
        fileIOSystemUtil.setFlagForCheckingSource(true);
        String removeFileExtension = fileIOSystemUtil.removeFileExtension(fileDetail.getFileName());
        Long driveId = Long.valueOf(fileIOSystemUtil.splitDriveIdFromFileName(removeFileExtension));
        Drive drive = driveDAO.findById(driveId);
        DriveState driveState = drive.getDriveStatesList().get(0);
        Document document = new Document();
        document.setDocument_name(removeFileExtension);
        document.setParent_id(driveState.getId());
        document.setParent_type(DriveState.class.getSimpleName());
        document.setCreator_user_id(drive.getCreatedUserid());
        document.setDocument_size(fileDetail.getSize());
        document.setDocument_extension(fileIOSystemUtil.splitFileExtension(fileDetail.getFileName()));
        document.setDocument_type(documentTypeDAO.getAllDocumentTypes().get(0));
        fileIOSystemUtil.createDirectoryIfNotExists(DriveState.class.getSimpleName(),driveState.getId().toString());
        document.setDocument_path(fileIOSystemUtil.getFileDirectoryForDatabase());
        documentDAO.save(document);

        try {
            fileIOSystemUtil.saveNewFile(uploadedInputStream,fileDetail.getFileName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.status(200)
                .entity("File saved to " + fileDetail.getFileName()).build();
    }


}
