package br.unitins.ecommerce.resource;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.pizza.PizzaDTO;
import br.unitins.ecommerce.dto.pizza.PizzaResponseDTO;
import br.unitins.ecommerce.service.pizza.PizzaService;

@Path("/pizzas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PizzaResource {

    @Inject
    PizzaService pizzaService;

    @GET
    public List<PizzaResponseDTO> getAll() {

        return pizzaService.getAll();
    }

    @GET
    @Path("/{id}")
    public PizzaResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {

        return pizzaService.getById(id);
    }

    @POST
    @Transactional
    public Response insert(PizzaDTO pizzaDto) {

        try {

            return Response
                    .status(Status.CREATED) // 201
                    .entity(pizzaService.insert(pizzaDto))
                    .build();
        } catch (ConstraintViolationException e) {

            Result result = new Result(e.getConstraintViolations());

            return Response
                    .status(Status.NOT_FOUND)
                    .entity(result)
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, PizzaDTO pizzaDto) {

        try {

            pizzaService.update(id, pizzaDto);

            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();
        } catch (ConstraintViolationException e) {

            Result result = new Result(e.getConstraintViolations());

            return Response
                    .status(Status.NOT_FOUND)
                    .entity(result)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException {

        pizzaService.delete(id);

        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/count")
    public Long count() {

        return pizzaService.count();
    }

    @GET
    @Path("/searchByNome/{nome}")
    public List<PizzaResponseDTO> getByNome(@PathParam("nome") String nome) {

        return pizzaService.getByNome(nome);
    }

    @GET
    @Path("/searchByCategoria/{categoria}")
    public List<PizzaResponseDTO> getByCategoria(@PathParam("categoria") Integer id) throws IndexOutOfBoundsException {

        return pizzaService.getByCategoria(id);
    }

    @GET
    @Path("/searchByTamanhoEmbalagem/{tamanhoEmbalagem}")
    public List<PizzaResponseDTO> getByTamanhoEmbalagem(@PathParam("tamanhoEmbalagem") Integer id) throws IndexOutOfBoundsException {

        return pizzaService.getByTamanhoEmbalagem(id);
    }

    @GET
    @Path("/searchByTamanhoPizza/{tamanhoPizza}")
    public List<PizzaResponseDTO> getByTamanhoPizza(@PathParam("tamanhoPizza") Integer id) throws IndexOutOfBoundsException {

        return pizzaService.getByTamanhoPizza(id);
    }

    @GET
    @Path("/searchByMarca/{marca}")
    public List<PizzaResponseDTO> getByMarca (@PathParam("marca") String nomeMarca) throws NullPointerException {

        return pizzaService.getByMarca(nomeMarca);
    }

    @GET
    @Path("/filterByPrecoMin/{precoMin}")
    public List<PizzaResponseDTO> filterByPrecoMin (@PathParam("precoMin") Double preco) {

        return pizzaService.filterByPrecoMin(preco);
    }

    @GET
    @Path("/filterByPrecoMax/{precoMax}")
    public List<PizzaResponseDTO> filterByPrecoMax (@PathParam("precoMax") Double preco) {

        return pizzaService.filterByPrecoMax(preco);
    }

    @GET
    @Path("/filterByEntrePreco/{precoMin}&{precoMax}")
    public List<PizzaResponseDTO> filterByEntrePreco (@PathParam("precoMin") Double precoMin, @PathParam("precoMax") Double precoMax) {

        return pizzaService.filterByEntrePreco(precoMin, precoMax);
    }
    
}
