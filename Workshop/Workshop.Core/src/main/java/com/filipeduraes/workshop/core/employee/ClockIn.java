// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.employee;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClockIn
{
    private final ClockInType type;
    private final UUID employeeID;
    private final LocalDateTime timestamp;

    public ClockIn(ClockInType type, UUID employeeID)
    {
        this.type = type;
        this.employeeID = employeeID;
        timestamp = LocalDateTime.now();
    }

    public ClockInType getType()
    {
        return type;
    }

    public UUID getEmployeeID()
    {
        return employeeID;
    }

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }
}