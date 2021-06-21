package com.example.demo.endpoints;

import com.example.demo.repositories.StudentRepository;
import jaxb.classes.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.example.demo.model.Student;

import java.util.List;
import java.util.Optional;

@Endpoint
public class StudentEndpoint {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentEndpoint(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PayloadRoot(namespace = "https://rca.ac.rw/verie/soap-app", localPart = "NewStudentDTORequest")
    @ResponsePayload
    public NewStudentDTOResponse create(@RequestPayload NewStudentDTORequest dto) {
        jaxb.classes.Student __student = dto.getStudent();
        Student _student = new Student(__student.getFirstName(), __student.getFirstName(), __student.getGender());
        Student student = studentRepository.save(_student);
        NewStudentDTOResponse studentDTO = new NewStudentDTOResponse();
        __student.setId(student.getId());
        studentDTO.setStudent(__student);
        return studentDTO;
    }

    @PayloadRoot(namespace = "https://rca.ac.rw/verie/soap-app", localPart = "GetAllStudentsRequest")
    @ResponsePayload
    public GetAllStudentsResponse findAll(@RequestPayload GetAllStudentsRequest request){

        List<Student> students = studentRepository.findAll();

        GetAllStudentsResponse response = new GetAllStudentsResponse();

        for (Student student: students){
            jaxb.classes.Student _student = new jaxb.classes.Student();
            _student.setId(student.getId());
            _student.setFirstName(student.getFirstName());
            _student.setLastName(student.getLastName());
            _student.setGender(student.getGender());
            response.getStudent().add(_student);
        }

        return response;
    }

    @PayloadRoot(namespace = "https://rca.ac.rw/verie/soap-app", localPart = "GetStudentDetailsRequest")
    @ResponsePayload
    public GetStudentDetailsResponse findById(@RequestPayload GetStudentDetailsRequest request){
        Optional<Student> _student = studentRepository.findById(request.getId());

        if(!_student.isPresent())
            return new GetStudentDetailsResponse();

        Student student = _student.get();

        GetStudentDetailsResponse response = new GetStudentDetailsResponse();

        jaxb.classes.Student __student = new jaxb.classes.Student();
        __student.setId(student.getId());
        __student.setFirstName(student.getFirstName());
        __student.setLastName(student.getLastName());
        __student.setGender(student.getGender());


        response.setStudent(__student);

        return response;
    }

    @PayloadRoot(namespace = "https://rca.ac.rw/anselme/soap-app", localPart = "DeleteStudentRequest")
    @ResponsePayload
    public DeleteStudentResponse delete(@RequestPayload DeleteStudentRequest request){
        studentRepository.deleteById(request.getId());
        DeleteStudentResponse response = new DeleteStudentResponse();
        response.setMessage("Successfully deleted a message");
        return response;
    }

    @PayloadRoot(namespace = "https://rca.ac.rw/anselme/soap-app", localPart = "UpdateStudentRequest")
    @ResponsePayload
    public UpdateStudentResponse update(@RequestPayload UpdateStudentRequest request){
        jaxb.classes.Student __student = request.getStudent();

        Student _student = new Student(__student.getFirstName(), __student.getFirstName(), __student.getGender());
        _student.setId(__student.getId());

        Student student = studentRepository.save(_student);

        UpdateStudentResponse studentDTO = new UpdateStudentResponse();

        __student.setId(student.getId());

        studentDTO.setStudent(__student);

        return studentDTO;
    }
}
