package com.nttdata.costoconversion.domain.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "purchases")
@ToString
public class PurchasesEntity implements Serializable{
	
	private static final long serialVersionUID = 482985440037322613L;
	
	@Id
    @SequenceGenerator(name="seq_purchases",
                       sequenceName="seq_purchases",
                       allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator="seq_purchases")
    @Column(name = "id")  
    private Integer id;
    
	@Column(name = "date")
    private Date date;
	
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "purchase_id")
    private String purchaseId;
    
    @Column(name = "model")
    private String model;
    
    @Column(name = "cryptocurrency")
    private String cryptoCurrency;
    
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "conversion_id")
    private ConversionEntity conversion;
    
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "versions_id")
    private VersionsEntity versions;

}
