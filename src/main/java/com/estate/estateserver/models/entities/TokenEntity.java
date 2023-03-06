package com.estate.estateserver.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TOKENS")
public class TokenEntity implements Serializable {

  public static final long serialVersionUID = 2456154887452124L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "token", unique = true)
  private String token;

  @Enumerated(EnumType.STRING)
  @Column(name = "token_type")
  private TokenType tokenType = TokenType.BEARER;
  @Column(name = "revoked")

  private boolean revoked;
  @Column(name = "expired")

  private boolean expired;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
