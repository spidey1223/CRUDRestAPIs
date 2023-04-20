package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController
{
	@Autowired
	JdbcTemplate jtemp;
	
	
	@RequestMapping(value="/product/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Object>delete(@PathVariable("id")String id)
	{
        jtemp.update("delete from product where id=?",id);
         return new ResponseEntity<>("product is deleted succesfully",HttpStatus.OK);
}
	@RequestMapping(value="/product/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Object>UpdateProduct(@PathVariable("id")String id,@RequestBody Product product)
	{
		jtemp.update("update product set name=? where id=?",product.getName(),id);
		return new ResponseEntity<>("product is updated succesfully",HttpStatus.OK);
	}
	
	@RequestMapping(value="/product",method=RequestMethod.POST)
	public ResponseEntity<Object>CreateProduct(@RequestBody Product product)
	{
		jtemp.update("insert into product values(?,?)",product.getId(),product.getName());
		return new ResponseEntity<> ("product is created succesfully",HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/product")
	public List<Product> getproduct()
	{
		List<Product> l=new ArrayList<>();
		
		jtemp.query("select * from product", new RowMapper()
		{

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
			{
				Product p=new Product();
				p.setId(rs.getString(1));
				p.setName(rs.getString(2));
				l.add(p);
				return p;
			
				
			}
			
		});
		return l;
		
	}
	}
	
