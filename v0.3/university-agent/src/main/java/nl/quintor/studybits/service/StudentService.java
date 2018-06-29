package nl.quintor.studybits.service;

import nl.quintor.studybits.entity.Student;
import nl.quintor.studybits.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Component
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public void setConnectionData(String studentId, String myDid, String requestNonce) {
        Student student = studentRepository.getStudentByStudentId(studentId);

        if (student == null) {
            throw new EntityNotFoundException("Student not found for studentId: " + studentId);
        }

        student.setMyDid(myDid);
        student.setRequestNonce(requestNonce);
        studentRepository.saveAndFlush(student);
    }

    @Transactional
    public Student getStudentByStudentId(String studentId) {
        return studentRepository.getStudentByStudentId(studentId);
    }

    @Transactional
    public Student getStudentByStudentDid(String studentDid) {
        return studentRepository.getStudentByStudentDid(studentDid);
    }

    @Transactional
    public String getMyDidByRequestNonce(String requestNonce) {
        return studentRepository.getStudentByRequestNonce(requestNonce).getMyDid();
    }

    @Transactional
    public void setStudentDid(String studentId, String studentDid) {
        Student student = studentRepository.getStudentByStudentId(studentId);

        if (student == null) {
            throw new EntityNotFoundException("Student not found for studentId: " + studentId);
        }

        student.setStudentDid(studentDid);
        studentRepository.saveAndFlush(student);
    }
}
