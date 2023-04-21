package br.unitins.ecommerce.service.pizza;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import br.unitins.ecommerce.dto.pizza.PizzaDTO;
import br.unitins.ecommerce.dto.pizza.PizzaResponseDTO;
import br.unitins.ecommerce.model.produto.pizza.Categoria;
import br.unitins.ecommerce.model.produto.pizza.Pizza;
import br.unitins.ecommerce.model.produto.pizza.Sabor;
import br.unitins.ecommerce.model.produto.pizza.TamanhoEmbalagem;
import br.unitins.ecommerce.model.produto.pizza.TamanhoPizza;
import br.unitins.ecommerce.repository.MarcaRepository;
import br.unitins.ecommerce.repository.PizzaRepository;
import br.unitins.ecommerce.repository.SaborRepository;
import br.unitins.ecommerce.service.avaliacao.AvaliacaoService;
import br.unitins.ecommerce.service.usuario.UsuarioService;

@ApplicationScoped
public class PizzaImplService implements PizzaService{

    @Inject
    PizzaRepository pizzaRepository;

    @Inject
    SaborRepository saborRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    AvaliacaoService avaliacaoService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    Validator validator;

    @Override
    public List<PizzaResponseDTO> getAll() {

        //List<Pizza> listaPizza = pizzaRepository.findAll().list();

        List<PizzaResponseDTO> listaPizzaResponseDTOs = new ArrayList<>();

        for (Pizza pizza : pizzaRepository.findAll().list()) {
            
            listaPizzaResponseDTOs.add(new PizzaResponseDTO(pizza));
        }

        return listaPizzaResponseDTOs;

        // return pizzaRepository.findAll()
        //         .stream()
        //         .map(pizza -> new PizzaResponseDTO(pizza))
        //         .toList();
    }

    @Override
    public PizzaResponseDTO getById(Long id) throws NotFoundException {

        Pizza pizza = pizzaRepository.findById(id);

        if (pizza == null)
            throw new NotFoundException("Não encontrado");

        return new PizzaResponseDTO(pizza);
    }

    @Override
    public PizzaResponseDTO insert(PizzaDTO pizzaDto) throws ConstraintViolationException {

        validar(pizzaDto);

        Pizza entity = new Pizza();

        entity.setNome(pizzaDto.nome());

        entity.setSabor(insertListaSabor(pizzaDto.idSabores()));

        entity.setBordaPizza(pizzaDto.bordaPizza());

        entity.setMarca(marcaRepository.findById(pizzaDto.idMarca()));

        entity.setPreco(pizzaDto.preco());

        entity.setEstoque(pizzaDto.estoque());

        entity.setCategoria(Categoria.valueOf(pizzaDto.categoria()));
        entity.setTamanhoEmbalagem(TamanhoEmbalagem.valueOf(pizzaDto.tamanhoEmbalagem()));
        entity.setTamanhoPizza(TamanhoPizza.valueOf(pizzaDto.tamanhoPizza()));

        pizzaRepository.persist(entity);

        return new PizzaResponseDTO(entity);
    }

    @Override
    public PizzaResponseDTO update(Long id, PizzaDTO pizzaDto) throws ConstraintViolationException {

        validar(pizzaDto);

        Pizza entity = pizzaRepository.findById(id);

        entity.setNome(pizzaDto.nome());

        entity.setSabor(insertListaSabor(pizzaDto.idSabores()));

        entity.setBordaPizza(pizzaDto.bordaPizza());

        entity.setMarca(marcaRepository.findById(pizzaDto.idMarca()));

        entity.setPreco(pizzaDto.preco());

        entity.setEstoque(pizzaDto.estoque());

        entity.setCategoria(Categoria.valueOf(pizzaDto.categoria()));
        entity.setTamanhoEmbalagem(TamanhoEmbalagem.valueOf(pizzaDto.tamanhoEmbalagem()));
        entity.setTamanhoPizza(TamanhoPizza.valueOf(pizzaDto.tamanhoPizza()));

        return new PizzaResponseDTO(entity);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Pizza pizza = pizzaRepository.findById(id);

        deleteListaSabor(pizza);

        avaliacaoService.delete(pizza);

        usuarioService.deleteProdutoFromListaDesejo(pizza);

        if (pizzaRepository.isPersistent(pizza))
            pizzaRepository.delete(pizza);

        else
            throw new NotFoundException("Nenhuma pizza encontrada");
    }

    @Override
    public Long count() {

        return pizzaRepository.count();
    }

    @Override
    public List<PizzaResponseDTO> getByNome(String nome) throws NullPointerException {

        List<Pizza> list = pizzaRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhuma pizza encontrada");

        return list.stream()
                    .map(PizzaResponseDTO::new)
                    .collect(Collectors.toList());
    }
    
    @Override
    public List<PizzaResponseDTO> getByCategoria(Integer id) throws IndexOutOfBoundsException, NullPointerException {

        if (id < 1 || id > 2)
            throw new IndexOutOfBoundsException("número fora das opções");

        List<Pizza> list = pizzaRepository.findByCategoria(Categoria.valueOf(id));

        if (list == null)
            throw new NullPointerException("Nenhuma pizza encontrada");

        return list.stream()
                    .map(PizzaResponseDTO::new)
                    .collect(Collectors.toList());
    }

    public List<PizzaResponseDTO> getByTamanhoPizza(Integer id) throws IndexOutOfBoundsException, NullPointerException {

        if (id < 1 || id > 5)
            throw new IndexOutOfBoundsException("número fora das opções");

        List<Pizza> list = pizzaRepository.findByTamanhoPizza(TamanhoPizza.valueOf(id));

        if (list == null)
            throw new NullPointerException("Nenhuma pizza encontrada");

        return list.stream()
                    .map(PizzaResponseDTO::new)
                    .collect(Collectors.toList());
    }

    public List<PizzaResponseDTO> getByTamanhoEmbalagem(Integer id) throws IndexOutOfBoundsException, NullPointerException {

        if (id < 1 || id > 5)
            throw new IndexOutOfBoundsException("número fora das opções");

        List<Pizza> list = pizzaRepository.findByTamanhoEmbalagem(TamanhoEmbalagem.valueOf(id));

        if (list == null)
            throw new NullPointerException("Nenhuma pizza encontrada");

        return list.stream()
                    .map(PizzaResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public List<PizzaResponseDTO> getByMarca(String nome) throws NullPointerException {

        List<Pizza> list = pizzaRepository.findByMarca(marcaRepository.findByNome(nome).get(0));

        if (list == null)
            throw new NullPointerException("Nenhuma marca encontrada");

        return list.stream()
                    .map(PizzaResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public List<PizzaResponseDTO> filterByPrecoMin(Double preco) throws NullPointerException {
        
        List<Pizza> list = pizzaRepository.filterByPrecoMinimo(preco);

        if (list == null)
            throw new NullPointerException("Nenhuma pizza encontrada");

        return list.stream()
                    .map(PizzaResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public List<PizzaResponseDTO> filterByPrecoMax(Double preco) {
        
        List<Pizza> list = pizzaRepository.filterByPrecoMaximo(preco);

        if (list == null)
            throw new NullPointerException("Nenhuma pizza encontrada");

        return list.stream()
                    .map(PizzaResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public List<PizzaResponseDTO> filterByEntrePreco(Double precoMin, Double precoMax) {
        
        List<Pizza> list = pizzaRepository.filterByEntrePreco(precoMin, precoMax);

        if (list == null)
            throw new NullPointerException("Nenhuma pizza encontrada");

        return list.stream()
                    .map(PizzaResponseDTO::new)
                    .collect(Collectors.toList());
    }

    private List<Sabor> insertListaSabor(List<Long> listaIdSabor) {

        List<Sabor> listaSabores = new ArrayList<>();

        for (Long idSabor : listaIdSabor) {
            
            listaSabores.add(saborRepository.findById(idSabor));
        }

        return listaSabores;
    }

    private void deleteListaSabor(Pizza pizza) {

        List<Long> listaIdsSabor = new ArrayList<>();

        for (Sabor sabor : pizza.getSabor()) {
            
            listaIdsSabor.add(sabor.getId());
        }

        for (Long idSabor : listaIdsSabor) {
            
            pizza.getSabor().remove(saborRepository.findById(idSabor));
        }
    }

    private void validar(PizzaDTO pizzaDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<PizzaDTO>> violations = validator.validate(pizzaDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
