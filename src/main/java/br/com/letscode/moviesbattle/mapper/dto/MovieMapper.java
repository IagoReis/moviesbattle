package br.com.letscode.moviesbattle.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.letscode.moviesbattle.data.entity.MovieEntity;
import br.com.letscode.moviesbattle.dto.MovieResponseDto;

@Mapper
public abstract class MovieMapper {

	public static final MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);
	
	@Mapping(target = "idFilme", source = "id")
	@Mapping(target = "titulo", source = "title")
	public abstract MovieResponseDto map(final MovieEntity entity);
	
}
