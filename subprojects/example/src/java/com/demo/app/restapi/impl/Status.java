package com.demo.app.restapi.impl;


import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.compiere.order.MOrder;
import org.compiere.order.MOrderLine;
import org.compiere.product.MProduct;
import org.idempiere.common.util.Env;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("status")
public class Status {

    @GET
    @Produces("text/plain")
    public String getStatus() {
        return "!!active!!";
    }
    
    @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createOrder(
			@FormParam("productId") int prodId,
			@FormParam("qty") int qty) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			//MOrder ord = orderService.createOrderWithProduct(prodId, qty);
			MOrder ord = new MOrder(Env.getCtx(), 0, "marketplacePOC");
			MOrderLine prodLine = new MOrderLine(ord);
	        MProduct prod = MProduct.get(Env.getCtx(), prodId);
	        prodLine.setProduct(prod);
	        prodLine.setQty(BigDecimal.valueOf(qty));
	        ord.saveEx("marketplacePOC");
	        prodLine.save("marketplacePOC");
			result = mapper.writeValueAsString(ord);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
