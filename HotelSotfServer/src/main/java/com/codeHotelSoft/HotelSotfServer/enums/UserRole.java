package com.codeHotelSoft.HotelSotfServer.enums;

/**
 * Define los roles disponibles para los usuarios del sistema.
 *
 * Se utiliza para controlar permisos y accesos en la aplicación.
 */
public enum UserRole {

    /** Usuario con privilegios de administración (gestión completa del sistema). */
    ADMIN,

    /** Usuario cliente, con permisos limitados a sus operaciones. */
    CUSTOMER
}
