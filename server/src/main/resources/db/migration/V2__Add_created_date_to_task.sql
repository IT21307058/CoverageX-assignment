-- V1__Add_created_date_to_task.sql

ALTER TABLE task 
ADD COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
