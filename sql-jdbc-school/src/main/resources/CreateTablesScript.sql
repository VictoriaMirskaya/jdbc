DROP TABLE IF EXISTS public.groups;
CREATE TABLE public.groups(group_id  SERIAL PRIMARY KEY, name text NOT NULL);
DROP TABLE IF EXISTS public.students;
CREATE TABLE public.students(student_id  SERIAL PRIMARY KEY, group_id integer, first_name text NOT NULL, last_name text NOT NULL);
DROP TABLE IF EXISTS public.courses;
CREATE TABLE public.courses(course_id SERIAL PRIMARY KEY, course_name text NOT NULL, course_description text);
DROP TABLE IF EXISTS public.students_courses;
CREATE TABLE public.students_courses(student_id integer NOT NULL, course_id integer NOT NULL);