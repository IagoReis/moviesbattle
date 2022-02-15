package br.com.letscode.moviesbattle.mapper.dto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.letscode.moviesbattle.data.entity.BattleEntity;
import br.com.letscode.moviesbattle.dto.QuizDto;

@Mapper
public abstract class QuizMapper {

	public static final QuizMapper INSTANCE = Mappers.getMapper(QuizMapper.class);
	
	@Mapping(target = "usuario", source = "user.login")
	@Mapping(target = "idQuiz", source = "id")
	@Mapping(target = "status", source = "status")
	@Mapping(target = "pontos", source = "points")
	@Mapping(target = "rodadas", source = "total")
	@Mapping(target = "erros", source = "mistakes")
	@Mapping(target = "porcentagemAcerto", source = "percent")
	public abstract QuizDto map(final BattleEntity entity);
	
	public abstract List<QuizDto> map(final List<BattleEntity> entity);
	
}
