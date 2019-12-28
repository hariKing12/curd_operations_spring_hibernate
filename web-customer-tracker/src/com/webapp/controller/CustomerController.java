package com.webapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.webapp.dao.CustomerDAO;
import com.webapp.entity.Customer;
import com.webapp.entity.UsersData;
import com.webapp.entity.UsersData.User;
import com.webapp.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customeService;

	@Autowired
	MultipartResolver multipartResolver;

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	private void initMultipartResolver(ApplicationContext context) {
		try {
			this.multipartResolver = context.getBean("multipartResolver", MultipartResolver.class);
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("Using MultipartResolver [" + this.multipartResolver + "]");
			}
		} catch (NoSuchBeanDefinitionException ex) {
			this.multipartResolver = null;
			if (this.logger.isDebugEnabled())
				this.logger.debug(
						"Unable to locate MultipartResolver with name 'multipartResolver': no multipart request handling provided");
		}
	}

	@GetMapping("/list")
	public String listCustomers(Model model) {
		List<Customer> customers = customeService.getCustomers();

		model.addAttribute("customers", customers);

		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showForm(Model model) {

		Customer customer = new Customer();

		model.addAttribute("customer", customer);

		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {

		customeService.saveCustomer(customer);
		return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") Integer id, Model model) {

		Customer customer = customeService.getById(id);

		model.addAttribute("customer", customer);

		return "customer-form";
	}

	@GetMapping("/delete")
	public String deleteUser(@RequestParam("customerId") int id) {
		customeService.deleteById(id);
		return "redirect:/customer/list";
	}

	@GetMapping("/search")
	public String searchCustomer(@RequestParam("searchName") String name, Model model) {
		System.out.println("Connection to service");
		List<Customer> customers = customeService.searchCustomer(name);
		model.addAttribute("customers", customers);
		System.out.println("Came back from service");
		return "list-customers";
	}

	@RequestMapping(value = "/uploadFile", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public String uploadXml(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
		File serverFile = null;
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tempFiles");
				if (!dir.exists()) {
					dir.mkdirs();
					serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					parseXMlDocument(serverFile);
				} else if (dir.exists()) {
					serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					parseXMlDocument(serverFile);
				}

			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
		return "redirect:/customer/list";
	}

	private int parseXMlDocument(File serverFile) {

		JAXBContext jaxbContext;
		Customer cus = new Customer();
		List<Customer> list=new ArrayList<>();
		try {
			jaxbContext = JAXBContext.newInstance(UsersData.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			UsersData users = (UsersData) jaxbUnmarshaller.unmarshal(serverFile);
			
			for(int i=0;i<users.getUser().size();i++) {

				for (User user : users.getUser()) {
					cus.setFirstName(user.getFirstName());
					cus.setLastname(user.getLastname());
					cus.setEmail(user.getEmail());
					list.add(cus);
				}
				break;
			}
			customeService.saveCustomer(list);
             
            
			return 1;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
