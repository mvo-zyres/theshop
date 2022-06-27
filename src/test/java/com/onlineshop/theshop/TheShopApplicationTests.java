package com.onlineshop.theshop;

import com.onlineshop.theshop.service.*;
import com.onlineshop.theshop.shop.model.store.*;
import com.onlineshop.theshop.shop.model.user.Address;
import com.onlineshop.theshop.shop.model.user.Role;
import com.onlineshop.theshop.shop.model.user.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class TheShopApplicationTests {

	@Autowired
	BillingAddressService billingAddressService;

	@Autowired
	ShippingAddressService shippingAddressService;

	@Autowired
	CartService cartService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ConvertService convertService;

	@Autowired
	OrderItemService orderItemService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderStatusService orderStatusService;

	@Autowired
	PrivilegeService privilegeService;

	@Autowired
	ProductImageService productImageService;

	@Autowired
	ProductService productService;

	@Autowired
	ProductTypeService productTypeService;

	@Autowired
	RoleService roleService;

	@Autowired
	StoreService storeService;

	@Autowired
	UserService userService;

	final Logger logger = LoggerFactory.getLogger(TheShopApplicationTests.class);

	private UUID testUserId = UUID.randomUUID();
	private Address testAddress = new Address(UUID.randomUUID(), "firstname", "lastname", "line1", "line2", "line3", "city", "zipOrPostcode", "countryProvince", "country");
	private User testUser = new User(
			testUserId,
			"addUserForm.getName()",
			"addUserForm.getFirstname()",
			"addUserForm.getLastname()",
			"addUserForm.getEmail()",
			"addUserForm.getPassword()",
			"addUserForm.getImg()",
			true,
			BigDecimal.ZERO
	);
	private Product testProduct = new Product(UUID.randomUUID(),"testProduct","test",new BigDecimal(20),UUID.randomUUID(),new BigDecimal(10));
	private Store testStore = new Store("TestStore",new BigDecimal(2));
	private Category testCategory = new Category(UUID.randomUUID(), "TestCategory","/testCategory",new BigDecimal(20));
	private Role testRole = new Role(UUID.randomUUID(), "TestRole", List.of());
	private ProductType testProductType = new ProductType("TestType",false);
	private Order testOrder = new Order();

	@Test
	void BillingAddressTest() {
		roleService.addRole(testRole);
		userService.addUser(testUser, testRole.getId());
		Address testBillingAddress = new Address(testUser, "firstname", "lastname", "line1", "line2", "line3", "city", "zipOrPostcode", "countryProvince", "country");
		UUID billingAddressId = testBillingAddress.getAddressId();
		billingAddressService.addBillingAddress(testBillingAddress);
		Assert.notNull(billingAddressService.getBillingAddressById(billingAddressId),"billingAddress not found");
		Assert.notNull(billingAddressService.getAllBillingAddresses(),"No billingAddresses found");
		testBillingAddress.setFirstname("firstname2");
		billingAddressService.updateBillingAddress(testBillingAddress);
		Assert.isTrue(billingAddressService.getBillingAddressById(billingAddressId).getFirstname().equalsIgnoreCase("firstname2"),"billingAddress not updated");
		billingAddressService.deleteBillingAddress(billingAddressId);
		Assert.isTrue(!billingAddressService.billingAddressExists(billingAddressId),"billingAddress not deleted");
		userService.deleteUser(testUserId);
		roleService.deleteRoleById(testRole.getId());
	}

	@Test
	void ShippingAddressTest() {
		roleService.addRole(testRole);
		userService.addUser(testUser, testRole.getId());
		Address testShippingAddress = new Address(testUser, "firstname", "lastname", "line1", "line2", "line3", "city", "zipOrPostcode", "countryProvince", "country");
		UUID shippingAddressId = testShippingAddress.getAddressId();
		shippingAddressService.addShippingAddress(testShippingAddress);
		Assert.notNull(shippingAddressService.getShippingAddressById(shippingAddressId),"shippingAddress not found");
		Assert.notNull(shippingAddressService.getAllShippingAddresses(),"No shippingAddress found");
		testShippingAddress.setFirstname("firstname2");
		shippingAddressService.updateShippingAddress(testShippingAddress);
		Assert.isTrue(shippingAddressService.getShippingAddressById(shippingAddressId).getFirstname().equalsIgnoreCase("firstname2"),"shippingAddress not updated");
		shippingAddressService.deleteShippingAddress(shippingAddressId);
		Assert.isTrue(!shippingAddressService.shippingAddressExists(shippingAddressId),"ShippingAddress not deleted");
		userService.deleteUser(testUserId);
		roleService.deleteRoleById(testRole.getId());
	}

	@Test
	void CartAndOrderTest() {
		roleService.addRole(testRole);
		userService.addUser(testUser, testRole.getId());
		Cart testCart = cartService.getCartByUserId(testUserId);
		Assert.notNull(testCart,"Cart not found");
		List<UUID> testProducts = new ArrayList<>();
		List<String> testTags = new  ArrayList<>();
		testTags.add("test");
		testProduct.setTags(testTags);
		testProduct.setImg(UUID.randomUUID());
		productTypeService.addProductType(testProductType);
		testProduct.setProductType(testProductType);
		productService.addProduct(testProduct);
		testProducts.add(testProduct.getId());
		cartService.addProductsToCart(testProducts,testCart.getId());
		Assert.notNull(cartService.getCartByUserId(testUserId).getProducts(),"Products not found");
		userService.loadCart(testUser);
		cartService.loadProducts(testUser.getCart());
		Assert.isTrue("22.0".equals(cartService.calcTotal(testUser).toString()),"Cart should cost 22.0 credits, calculated was " + cartService.calcTotal(testUser).toString() + " credits instead");
		cartService.deleteAllProductsFromCartByCartId(testCart.getId());
		Assert.isTrue(cartService.getCartByUserId(testUserId).getProducts().size()==0,"Products not deleted");
		//convert
		Address testBillingAddress = testAddress;
		Address testShippingAddress = testAddress;
		testBillingAddress.setAddressId(UUID.randomUUID());
		testShippingAddress.setAddressId(UUID.randomUUID());
		testBillingAddress.setUser(testUser);
		testShippingAddress.setUser(testUser);
		billingAddressService.addBillingAddress(testBillingAddress);
		shippingAddressService.addShippingAddress(testShippingAddress);
		Order order = convertService.cartToOrder(testCart,testBillingAddress,testShippingAddress);
		//Order
		orderService.addOrder(order);
		Assert.notNull(orderService.getOrderById(order.getId()),"Order not found");
		Assert.notNull(orderService.getAllOrders(),"Orders not found");
		billingAddressService.deleteBillingAddress(testBillingAddress.getAddressId());
		shippingAddressService.deleteShippingAddress(testShippingAddress.getAddressId());
		orderService.deleteOrder(order.getId());
		Assert.isTrue(!orderService.orderExists(order.getId()), "Order not deleted");
		orderService.calcTotal(order);
		productTypeService.deleteProductTypeById(testProductType.getId());
		testProduct.setProductType(null);
		userService.deleteUser(testUserId);
		roleService.deleteRoleById(testRole.getId());
	}

	@Test
	void ProductTest() {
		testProduct.setRecommended(true);
		List<String> testTags = new  ArrayList<>();
		testTags.add("test");
		testProduct.setTags(testTags);
		testProduct.setImg(UUID.randomUUID());
		productTypeService.addProductType(testProductType);
		testProduct.setProductType(testProductType);
		productService.addProduct(testProduct);
		Assert.notNull(productService.getProductById(testProduct.getId()),"Product not found");
		Assert.notNull(productService.getAllProducts(),"Products not found");
		Assert.notNull(productService.getRecommended(),"Recommended not found");
		testProduct.setRecommended(false);
		productService.updateProduct(testProduct);
		Product checkProduct = productService.getProductById(testProduct.getId());
		Assert.isTrue(!checkProduct.isRecommended(),"Product not updated");
		productService.deleteProduct(testProduct.getId());
		Assert.isTrue(!productService.productExists(testProduct.getId()),"Product not deleted");
		productTypeService.deleteProductTypeById(testProductType.getId());
		testProduct.setProductType(null);
	}

	@Test
	void ProductTypeTest() {
		UUID uuid = testProductType.getId();
		productTypeService.addProductType(testProductType);
		Assert.notNull(productTypeService.getProductTypeById(uuid),"ProductType not found");
		Assert.notNull(productTypeService.getAllProductTypes(),"ProductTypes not found");
		productTypeService.deleteProductTypeById(uuid);
		Assert.isTrue(!productTypeService.productTypeExists(uuid),"ProductType not deleted");
	}

	@Test
	void CategoryTest() {
		storeService.addStore(testStore);
		testCategory.setStore(testStore);
		UUID uuid = testCategory.getId();
		categoryService.addCategory(testCategory);
		Assert.notNull(categoryService.getCategoryById(uuid),"Category not found");
		Assert.isTrue(categoryService.categoryExists(uuid),"Category not found");
		Assert.notNull(categoryService.getAllCategories(),"Categories not found");
		Assert.isTrue(categoryService.nameExists(testCategory.getName()),"Category name not found");
		categoryService.deleteCategory(uuid);
		Assert.isTrue(!categoryService.categoryExists(uuid),"Category not deleted");
		storeService.deleteStore(testStore.getId());
		testCategory.setStore(null);
	}

	@Test
	void RoleTest() {
		UUID uuid = testRole.getId();
		roleService.addRole(testRole);
		Assert.notNull(roleService.getRoleById(uuid),"Role not found");
		Assert.notNull(roleService.getRoleByName(testRole.getName()),"Role not found");
		Assert.isTrue(roleService.roleExists(uuid),"Role not found");
		Assert.notNull(categoryService.getAllCategories(),"Roles not found");
		roleService.deleteRoleById(uuid);
		Assert.isTrue(!roleService.roleExists(uuid),"Role not deleted");
	}

	@Test
	void StoreTest() {
		UUID uuid = testStore.getId();
		storeService.addStore(testStore);
		Assert.notNull(storeService.getStoreById(uuid),"Store not found");
		Assert.notNull(storeService.getAllStores(),"Stores not found");
		storeService.deleteStore(uuid);
		Assert.isTrue(!storeService.storeExists(uuid),"Store not deleted");
	}

	@Test
	void UserTest() {
		UUID uuid = testUser.getId();
		roleService.addRole(testRole);
		userService.addUser(testUser, testRole.getId());
		User testUserTest = testUser;
		Address testBillingAddress = new Address(testUser, "firstname", "lastname", "line1", "line2", "line3", "city", "zipOrPostcode", "countryProvince", "country");
		Address testShippingAddress = new Address(testUser, "firstname", "lastname", "line1", "line2", "line3", "city", "zipOrPostcode", "countryProvince", "country");
		billingAddressService.addBillingAddress(testBillingAddress);
		shippingAddressService.addShippingAddress(testShippingAddress);
		testUserTest.setDefaultBillingAddress(testBillingAddress);
		testUserTest.setDefaultShippingAddress(testShippingAddress);
		Assert.isTrue(userService.userExists(uuid),"User not found");
		/*Assert.notNull(userService.getAllUser(),"Users not found");*/
		userService.deleteUser(uuid);
		Assert.isTrue(!userService.userExists(uuid),"User not deleted");
		billingAddressService.deleteBillingAddress(testBillingAddress.getAddressId());
		shippingAddressService.deleteShippingAddress(testShippingAddress.getAddressId());
		roleService.deleteRoleById(testRole.getId());
	}

	@Test
	void contextLoads() {

	}
}