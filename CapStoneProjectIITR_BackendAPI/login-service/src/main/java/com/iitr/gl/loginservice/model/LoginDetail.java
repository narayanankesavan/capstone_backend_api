package com.iitr.gl.loginservice.model;


import com.iitr.gl.loginservice.repository.MySqlLoginRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "aa")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginDetail
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long user_id;
    private String user_email;
    private String password;

}
