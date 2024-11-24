package ru.develop.schedule.extern.dto;

public record CreatePersonDTO(String firstName, String lastName, String phone, String email, String password, String repeatPassword) {
}
