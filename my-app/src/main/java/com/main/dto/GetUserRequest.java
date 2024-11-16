package com.main.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString()
public class GetUserRequest {
    public Integer page;

    public Integer size;
}
