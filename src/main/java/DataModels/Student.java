package DataModels;

import java.sql.Date;
import java.util.List;
import DataModels.Courses;

public class Student {
        private int document;
        private String name;
        private String lastname;
        private int age;
        private Date createdAt;
        private Date updatedAt;
        private Date deletedAt;
        private List<Courses> courses;

        public List<Courses> getCourses() {
          return courses;
      }

      public void setCourses(List<Courses> courses) {
          this.courses = courses;
      }
        
        public int getDocument() {
            return document;
        }

        public void setDocument(int document) {
            this.document = document;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Date getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Date deletedAt) {
            this.deletedAt = deletedAt;
        }
    }

