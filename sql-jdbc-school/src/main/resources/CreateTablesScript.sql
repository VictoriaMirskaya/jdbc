DROP TABLE IF EXISTS students_courses;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS courses;
CREATE TABLE groups(group_id INT GENERATED ALWAYS AS IDENTITY, group_name VARCHAR(255) NOT NULL, PRIMARY KEY(group_id));
CREATE TABLE courses(course_id INT GENERATED ALWAYS AS IDENTITY, course_name VARCHAR(255) NOT NULL, course_description VARCHAR(255), PRIMARY KEY(course_id));
CREATE TABLE students(student_id INT GENERATED ALWAYS AS IDENTITY, group_id INT, first_name VARCHAR(255) NOT NULL, last_name VARCHAR(255) NOT NULL, PRIMARY KEY(student_id), CONSTRAINT fk_group FOREIGN KEY(group_id) REFERENCES groups(group_id) ON DELETE CASCADE);
CREATE TABLE students_courses(student_id INT, course_id INT, CONSTRAINT fk_student FOREIGN KEY(student_id) REFERENCES students(student_id) ON DELETE CASCADE,  CONSTRAINT fk_course FOREIGN KEY(course_id) REFERENCES courses(course_id) ON DELETE CASCADE);