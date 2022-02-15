package br.com.letscode.moviesbattle.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.letscode.moviesbattle.data.entity.RoundEntity;
import br.com.letscode.moviesbattle.dto.RoundDto;

@Mapper(uses = MovieMapper.class)
public abstract class RoundMapper {

	public static final RoundMapper INSTANCE = Mappers.getMapper(RoundMapper.class);
	
	@Mapping(target = "rodada", source = "number")
	@Mapping(target = "idRodada", source = "id")
	@Mapping(target = "filmes", source = "movies")
	public abstract RoundDto map(final RoundEntity entity);
	
}
