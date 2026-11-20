package com.SALOUM.Gestion.de.production.d.impression.entities;

import com.SALOUM.Gestion.de.production.d.impression.enums.StatutRouleau;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "rouleau")
@CompoundIndex(name = "idx_rouleau_op_seq",
        def = "{'ordre_production_id': 1, 'numero_sequence': 1}", unique = true)
public class Rouleau extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    @Indexed(unique = true)
    @Field("code_qr")
    private String codeQr; // ex: R-OP2025-0001-01

    @NotBlank
    @Field("ordre_production_id")
    private String ordreProductionId;

    @NotNull
    @Field("numero_sequence")
    private Integer numeroSequence;

    @Digits(integer = 8, fraction = 2)
    @Field(name = "metrage", targetType = FieldType.DECIMAL128)
    private BigDecimal metrage;

    @NotNull
    @Indexed
    private StatutRouleau statut;

    @Size(max = 500)
    @Field("qr_code_path")
    private String qrCodePath;
}