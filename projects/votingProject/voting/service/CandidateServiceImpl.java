package service;

import enums.AuditAction;
import enums.AuditModule;
import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import file.CandidateFileManager;
import interfaces.AuditService;
import interfaces.CandidateService;
import model.Candidate;
import repository.CandidateRepository;
import repositoryImpl.CandidateRepositoryImpl;
import util.IDGenerator;
import validation.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

public class CandidateServiceImpl implements CandidateService {

    private CandidateRepository candidateRepository;
    private CandidateFileManager candidateFileManager;
    private AuditService auditService;

    public CandidateServiceImpl() {
        candidateRepository = new CandidateRepositoryImpl();
        candidateFileManager = new CandidateFileManager();
        auditService = new AuditServiceImpl();
        loadCandidates();
    }

    private void loadCandidates() {
        List<Candidate> candidates = candidateFileManager.load();
        for(Candidate candidate : candidates) {
            candidateRepository.save(candidate);
        }
    }

    private void saveCandidates(){
        candidateFileManager.save(candidateRepository.findAll());
    }


    @Override
    public boolean addCandidate(Candidate candidate) throws UserAlreadyExistsException, InputValidationException {
        validateCandidate(candidate);

        candidate.setNominationDate(LocalDate.now());
        candidate.setCandidateId(IDGenerator.generateId("CAN"));

        if(candidateRepository.exists(candidate.getCandidateId())) {
            throw new UserAlreadyExistsException("Candidate already present");
        }

        if(candidateRepository.existsByStudentId(candidate.getStudentId())) {
            throw new UserAlreadyExistsException("Student already registered as a Candidate");
        }

        boolean status = candidateRepository.save(candidate);

        if(status) {
            auditService.log(
                    AuditModule.CANDIDATE,
                    AuditAction.ADD,
                    "Candidate Added : " + candidate.getCandidateName()
            );
            saveCandidates();
        }

        return status;
    }


    private void validateCandidate(Candidate candidate) throws InputValidationException{
        if(candidate == null) {
            throw new InputValidationException("Candidate cannot be null");
        }

        if(ValidationUtil.isEmpty(candidate.getCandidateName())) {
            throw new InputValidationException("Candidate name required");
        }

        if(ValidationUtil.isValidEmail(candidate.getStudentId())) {
            throw new InputValidationException("Student ID required");
        }
    }


    @Override
    public boolean updateCandidate(Candidate candidate) throws UserNotFoundException, InputValidationException {
        validateCandidate(candidate);

        if(!candidateRepository.exists(candidate.getCandidateId())) {
            throw new UserNotFoundException("Candidate not found");
        }

        boolean status = candidateRepository.update(candidate);

        if(status) {
            auditService.log(
                    AuditModule.CANDIDATE,
                    AuditAction.UPDATE,
                    "Candidate Updated : " + candidate.getCandidateName()
            );
            saveCandidates();
        }

        return status;
    }



    @Override
    public boolean deleteCandidate(String candidateId) throws UserNotFoundException {
        if(!candidateRepository.exists(candidateId)) {
            throw new UserNotFoundException("Candidate Not Found");
        }

        boolean status = candidateRepository.delete(candidateId);

        if(status) {
            auditService.log(
                    AuditModule.CANDIDATE,
                    AuditAction.DELETE,
                    "Candidate Removed : "
            );
            saveCandidates();
        }

        return status;
    }


    @Override
    public Candidate searchCandidate(String candidateId) throws UserNotFoundException {
        Candidate candidate = candidateRepository.findById(candidateId);
        if(candidate == null) {
            throw new UserNotFoundException("Candidate not present");
        }
        return candidate;
    }


    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

}
