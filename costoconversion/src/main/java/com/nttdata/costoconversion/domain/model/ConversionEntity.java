package com.nttdata.costoconversion.domain.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * The class defines the domain object model
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "conversions")
@ToString
public class ConversionEntity implements Serializable{
	
	private static final long serialVersionUID = 482985440037322613L;
	
	@Id
    @SequenceGenerator(name="seq_conversion",
                       sequenceName="seq_conversion",
                       allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator="seq_conversion")
    @Column(name = "id")  
    private Integer id;
    
    @Column(name = "time_life")
    private Integer timeLife;
    
    @Column(name = "conversion_id")
    private String conversionId;
    
    @Column(name = "model")
    private String model;
    
    @Column(name = "cryptocurrency")
    private String cryptoCurrency;   
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "conversion_id", referencedColumnName = "id")
    private Set<VersionsEntity> versions;
        
}
