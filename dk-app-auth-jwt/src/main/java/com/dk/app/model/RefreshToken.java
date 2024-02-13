package com.dk.app.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefreshToken {

	@Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
    @SequenceGenerator(name = "refresh_token_seq", allocationSize = 1)
    private Long id;

    @Column(name = "TOKEN", nullable = false, unique = true)
    private String token;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_DEVICE_ID", unique = true)
    private UserDevice userDevice;

    @Column(name = "REFRESH_COUNT")
    private Long refreshCount;

    @Column(name = "EXPIRY_DT", nullable = false)
    private Instant expiryDate;
    
    public void incrementRefreshCount() {
        refreshCount = refreshCount + 1;
    }
}
