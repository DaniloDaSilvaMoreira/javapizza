package br.unitins.ecommerce.service.avaliacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import br.unitins.ecommerce.dto.avaliacao.AvaliacaoDTO;
import br.unitins.ecommerce.dto.avaliacao.AvaliacaoResponseDTO;
import br.unitins.ecommerce.model.produto.Produto;
import br.unitins.ecommerce.model.produto.avaliacao.Avaliacao;
import br.unitins.ecommerce.model.produto.avaliacao.Estrela;
import br.unitins.ecommerce.repository.AvaliacaoRepository;
import br.unitins.ecommerce.repository.PizzaRepository;
import br.unitins.ecommerce.repository.UsuarioRepository;

@ApplicationScoped
public class AvaliacaoImplService implements AvaliacaoService {

    @Inject
    AvaliacaoRepository avaliacaoRepository;
    
    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PizzaRepository pizzaRepository;

    @Inject
    Validator validator;

    @Override
    public List<AvaliacaoResponseDTO> getAll() {

        return avaliacaoRepository.findAll()
                .stream()
                .map(AvaliacaoResponseDTO::new)
                .toList();
    }

    @Override
    public AvaliacaoResponseDTO getById(Long id) throws NotFoundException {

        Avaliacao avaliacao = avaliacaoRepository.findById(id);

        if (avaliacao == null)
            throw new NotFoundException("Não encontrado");

        return new AvaliacaoResponseDTO(avaliacao);
    }

    @Override
    public AvaliacaoResponseDTO insert(AvaliacaoDTO avaliacaoDto) throws ConstraintViolationException {

        validar(avaliacaoDto);

        Avaliacao entity = new Avaliacao();

        entity.setComentario(avaliacaoDto.comentario());

        entity.setData(LocalDate.now());

        entity.setEstrela(Estrela.valueOf(avaliacaoDto.estrela()));

        entity.setProduto(pizzaRepository.findById(avaliacaoDto.idProduto()));

        entity.setUsuario(usuarioRepository.findById(avaliacaoDto.idUsuario()));

        avaliacaoRepository.persist(entity);

        return new AvaliacaoResponseDTO(entity);
    }

    @Override
    public AvaliacaoResponseDTO update(Long id, AvaliacaoDTO avaliacaoDto) throws ConstraintViolationException {

        validar(avaliacaoDto);

        Avaliacao entity = avaliacaoRepository.findById(id);

        entity.setComentario(avaliacaoDto.comentario());

        entity.setData(LocalDate.now());

        entity.setEstrela(Estrela.valueOf(avaliacaoDto.estrela()));

        entity.setProduto(pizzaRepository.findById(avaliacaoDto.idProduto()));

        entity.setUsuario(usuarioRepository.findById(avaliacaoDto.idUsuario()));

        return new AvaliacaoResponseDTO(entity);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Avaliacao avaliacao = avaliacaoRepository.findById(id);

        if (avaliacaoRepository.isPersistent(avaliacao))
            avaliacaoRepository.delete(avaliacao);

        else
            throw new NotFoundException("Nenhuma avaliação encontrado");
    }

    @Override
    public Long count() {

        return avaliacaoRepository.count();
    }

    @Override
    public List<AvaliacaoResponseDTO> getByYear(Integer year) throws NullPointerException {

        List<Avaliacao> list = avaliacaoRepository.findByYear(year);

        if (list == null)
            throw new NullPointerException("Nenhuma avaliação encontrada");

        return list.stream()
                    .map(AvaliacaoResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public void delete(Produto produto) {

        List<Avaliacao> listaAvaliacao = avaliacaoRepository.findByPizza(produto);

        for (Avaliacao avaliacao : listaAvaliacao) {
            
            avaliacaoRepository.delete(avaliacao);
        }
    }

    private void validar(AvaliacaoDTO avaliacaoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<AvaliacaoDTO>> violations = validator.validate(avaliacaoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
