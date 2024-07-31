package com.nttdata.costoconversion.domain.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The class defines the domain object model
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "versions")
public class VersionsEntity implements Serializable{
	
	private static final long serialVersionUID = 482985440037322613L;
	
	@Id
    @SequenceGenerator(name="seq_versions",
                       sequenceName="seq_versions",
                       allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator="seq_versions")
    @Column(name = "id")  
    private Integer id;
    
    @Column(name = "version")
    private String version;
    
    @Column(name = "priceusd")
    private Float priceUsd;
    
    @Column(name = "pricecryptocurrency")
    private Double priceCryptocurrency;
    
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "conversion_id")
    private ConversionEntity conversion; 

}
