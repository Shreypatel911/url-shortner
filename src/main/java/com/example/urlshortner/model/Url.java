package com.example.urlshortner.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "shorted-url")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Url {
    @Id
    private String id;
    private String originalLink;
    private String shortLink;
    private LocalDateTime createdDate;
    private LocalDateTime expirationDate;
}
