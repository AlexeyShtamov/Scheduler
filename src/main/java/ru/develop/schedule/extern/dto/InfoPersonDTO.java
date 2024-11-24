package ru.develop.schedule.extern.dto;

import java.util.TimeZone;

public record InfoPersonDTO(Long id, String firstName, String lastName, String email, TimeZone timeZone, String description, String phone, String telegram, String vk) {
}
