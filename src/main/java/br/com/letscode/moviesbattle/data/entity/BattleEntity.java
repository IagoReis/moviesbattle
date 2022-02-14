package br.com.letscode.moviesbattle.data.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.letscode.moviesbattle.enums.BattleStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "battle")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BattleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
	private UserEntity user;
	
	@Transient
	private List<RoundEntity> rounds;
	
	@Column
	private BattleStatusEnum status;
	
	@Column
	private Integer points;
	
	@Column
	private BigDecimal percent;
	
	@PrePersist
	private void prePersist() {
		status = BattleStatusEnum.WAITING;
	}
	
}
