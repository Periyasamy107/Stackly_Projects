package service;

import enums.AuditAction;
import enums.AuditModule;
import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import file.ElectionFileManager;
import interfaces.AuditService;
import interfaces.ElectionService;
import model.Election;
import repository.ElectionRepository;
import repositoryImpl.ElectionRepositoryImpl;
import util.IDGenerator;
import validation.ValidationUtil;

import java.util.List;

public class ElectionServiceImpl implements ElectionService {

    private ElectionRepository electionRepository;
    private ElectionFileManager electionFileManager;
    private AuditService auditService;

    public ElectionServiceImpl() {
        electionRepository = new ElectionRepositoryImpl();
        electionFileManager = new ElectionFileManager();
        auditService = new AuditServiceImpl();
        loadElections();
    }

    private void loadElections() {
        List<Election> elections = electionFileManager.load();
        for(Election election : elections) {
            electionRepository.save(election);
        }
    }

    private void saveElections() {
        electionFileManager.save(electionRepository.findAll());
    }

    @Override
    public boolean createElection(Election election) throws UserAlreadyExistsException, InputValidationException {

        validateElection(election);

        election.setElectionId(IDGenerator.generateId("ELEC"));

        if(electionRepository.exists(election.getElectionId())) {
            throw new UserAlreadyExistsException("Election already exists");
        }

        boolean status = electionRepository.save(election);

        if(status) {
            auditService.log(
                    AuditModule.ELECTION,
                    AuditAction.CREATE_ELECTION,
                    "Election Created : " + election.getElectionName()
            );
            saveElections();
        }

        return status;
    }


    private void validateElection(Election election) throws InputValidationException {

        if(election == null) {
            throw new InputValidationException("Election cannot be null");
        }

        if(ValidationUtil.isEmpty(election.getElectionName())) {
            throw new InputValidationException("Election name required");
        }

        if(ValidationUtil.isEmpty(election.getPosition())) {
            throw new InputValidationException("Position required");
        }
    }


    @Override
    public boolean updateElection(Election election) throws UserNotFoundException, InputValidationException {

        validateElection(election);

        if(!electionRepository.exists(election.getElectionId())) {
            throw new UserNotFoundException("Election not found");
        }

        boolean status = electionRepository.update(election);

        if(status) {
            auditService.log(
                    AuditModule.ELECTION,
                    AuditAction.UPDATE_ELECTION,
                    "Election Updated : " + election.getElectionName()
            );
            saveElections();
        }

        return  status;
    }


    @Override
    public boolean deleteElection(String electionId) throws UserNotFoundException{

        if(!electionRepository.exists(electionId)) {
            throw new UserNotFoundException("Election not found");
        }

        boolean status = electionRepository.delete(electionId);

        if(status) {
            auditService.log(
                    AuditModule.ELECTION,
                    AuditAction.DELETE_ELECTION,
                    "Election Closed : "
            );
            saveElections();
        }

        return status;
    }


    @Override
    public boolean startElection(String electionId) throws UserNotFoundException{
        Election election = searchElection(electionId);
        election.setStatus("ACTIVE");
        boolean status = electionRepository.update(election);
        if(status) {
            auditService.log(
                    AuditModule.ELECTION,
                    AuditAction.START_ELECTION,
                    "Election Started : " + election.getElectionName()
            );
            saveElections();
        }
        return status;
    }


    @Override
    public boolean closeElection(String electionId) throws UserNotFoundException {
        Election election = searchElection(electionId);
        election.setStatus("CLOSED");
        boolean status = electionRepository.update(election);
        if(status) {
            auditService.log(
                    AuditModule.ELECTION,
                    AuditAction.CLOSE_ELECTION,
                    "Election Closed : "
            );
            saveElections();
        }
        return status;
    }


    @Override
    public Election searchElection(String electionId) throws UserNotFoundException {
        Election election = electionRepository.findById(electionId);
        if(election == null) {
            throw new UserNotFoundException("Election not found");
        }
        return election;
    }


    @Override
    public List<Election> getallElections() {
        return electionRepository.findAll();
    }


}
