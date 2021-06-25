package com.example.demo.endpoints;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repositories.ICourseRepository;
import com.example.demo.repositories.StudentRepository;
import https.rca_ac_rw.verie.soap_app.*;
import https.rca_ac_rw.verie.soap_app.courses.GetAllCoursesRequest;
import https.rca_ac_rw.verie.soap_app.courses.GetAllCoursesResponse;
import https.rca_ac_rw.verie.soap_app.courses.NewCourseRequestDTO;
import https.rca_ac_rw.verie.soap_app.courses.NewCourseResponseDTO;
import https.rca_ac_rw.verie.soap_app.student.NewStudentDTORequest;
import https.rca_ac_rw.verie.soap_app.student.NewStudentDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseEndPoint {
    private final ICourseRepository courseRepository;

    @Autowired
    public CourseEndPoint(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @PayloadRoot(namespace = "https://rca.ac.rw/verie/soap-app/courses", localPart = "NewCourseRequestDTO")
    @ResponsePayload
    public NewCourseResponseDTO create(@RequestPayload NewCourseRequestDTO dto) {
        https.rca_ac_rw.verie.soap_app.courses.Course __Course = dto.getCourse();
        Course _course = new Course(__Course.getName(),__Course.getCode());
        Course course = courseRepository.save(_course);
        NewCourseResponseDTO courseResponseDTO = new NewCourseResponseDTO();
        __Course.setId(course.getId());
        courseResponseDTO.setCourse(__Course);
        return courseResponseDTO;
    }

//    @PayloadRoot(namespace = "https://rca.ac.rw/verie/soap-app/student", localPart = "NewStudentDTORequest")
//    @ResponsePayload
//    public NewStudentDTOResponse create(@RequestPayload NewStudentDTORequest dto) {
//        https.rca_ac_rw.verie.soap_app.student.Student __student = dto.getStudent();
//        Student _student = new Student(__student.getFirstName(), __student.getFirstName(), __student.getGender());
//        Student student = studentRepository.save(_student);
//        NewStudentDTOResponse studentDTO = new NewStudentDTOResponse();
//        __student.setId(student.getId());
//        studentDTO.setStudent(__student);
//        return studentDTO;
//    }


    @PayloadRoot(namespace = "https://rca.ac.rw/verie/soap-app/courses", localPart = "GetAllCoursesRequest")
    @ResponsePayload
    public GetAllCoursesResponse findAll(@RequestPayload GetAllCoursesRequest request){

        List<Course> courses = courseRepository.findAll();

        GetAllCoursesResponse response = new GetAllCoursesResponse();

        for (Course course: courses){
            https.rca_ac_rw.verie.soap_app.courses.Course _course = new https.rca_ac_rw.verie.soap_app.courses.Course();
            _course.setId(course.getId());
            _course.setName(course.getName());
            _course.setCode(course.getCode());
            response.getCourse().add(_course);
        }

        return response;
    }

}
