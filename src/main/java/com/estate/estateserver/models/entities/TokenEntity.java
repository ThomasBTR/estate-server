package com.estate.estateserver.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "TOKENS")
@Table(name = "TOKENS")
public class TokenEntity implements Serializable {

  public static final long serialVersionUID = 2456154887452124L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "token")
  private String token;

  @Enumerated(EnumType.STRING)
  @Column(name = "token_type")
  private TokenType tokenType = TokenType.BEARER;
  @Column(name = "revoked", columnDefinition = "TINYINT(1)")
  private boolean revoked;
  @Column(name = "expired", columnDefinition = "TINYINT(1)")
  private boolean expired;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id")
  private User user;
}
