package com.onlineshop.theshop.startup;

import com.onlineshop.theshop.shop.db.dao.PrivilegeDAO;
import com.onlineshop.theshop.shop.db.dao.RoleDAO;
import com.onlineshop.theshop.shop.db.dao.StoreDAO;
import com.onlineshop.theshop.shop.db.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InitDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    PrivilegeDAO privilegeDAO;

    @Autowired
    StoreDAO storeDAO;

    private static final String TABLE_SQL = "CREATE TABLE IF NOT EXISTS store\n" +
            "(\n" +
            "    store_id uuid NOT NULL,\n" +
            "    name text NOT NULL,\n" +
            "    tax decimal,\n" +
            "    PRIMARY KEY (store_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS user_billing_address\n" +
            "(\n" +
            "    address_id uuid NOT NULL,\n" +
            "    firstname text NOT NULL,\n" +
            "    lastname text NOT NULL,\n" +
            "    line1 text NOT NULL,\n" +
            "    line2 text,\n" +
            "    line3 text,\n" +
            "    city text NOT NULL,\n" +
            "    zip_or_postcode text NOT NULL,\n" +
            "    country_province text NOT NULL,\n" +
            "    country text NOT NULL,\n" +
            "    PRIMARY KEY (address_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS user_shipping_address\n" +
            "(\n" +
            "    address_id uuid NOT NULL,\n" +
            "    firstname text NOT NULL,\n" +
            "    lastname text NOT NULL,\n" +
            "    line1 text NOT NULL,\n" +
            "    line2 text,\n" +
            "    line3 text,\n" +
            "    city text NOT NULL,\n" +
            "    zip_or_postcode text NOT NULL,\n" +
            "    country_province text NOT NULL,\n" +
            "    country text NOT NULL,\n" +
            "    PRIMARY KEY (address_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS \"user\"\n" +
            "(\n" +
            "    user_id uuid NOT NULL,\n" +
            "    user_name text NOT NULL,\n" +
            "    firstname text NOT NULL,\n" +
            "    lastname text NOT NULL,\n" +
            "    email text NOT NULL,\n" +
            "    password text NOT NULL,\n" +
            "    img text,\n" +
            "    enabled boolean NOT NULL,\n" +
            "    credits decimal NOT NULL,\n" +
            "    default_billing_address_id uuid,\n" +
            "    default_shipping_address_id uuid,\n" +
            "    PRIMARY KEY (user_id),\n" +
            "    FOREIGN KEY (default_billing_address_id) REFERENCES user_billing_address (address_id),\n" +
            "    FOREIGN KEY (default_shipping_address_id) REFERENCES user_shipping_address (address_id)\n" +
            ");\n" +
            "\n" +
            "    ALTER TABLE user_billing_address\n" +
            "    ADD COLUMN IF NOT EXISTS user_id uuid;\n" +
            "    ALTER TABLE user_billing_address\n" +
            "    ADD FOREIGN KEY (user_id) REFERENCES \"user\" (user_id);\n" +
            "\n" +
            "    ALTER TABLE user_shipping_address\n" +
            "    ADD COLUMN IF NOT EXISTS user_id uuid;\n" +
            "    ALTER TABLE user_shipping_address\n" +
            "    ADD FOREIGN KEY (user_id) REFERENCES \"user\" (user_id);\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS cart\n" +
            "(\n" +
            "    cart_id uuid NOT NULL,\n" +
            "    user_id uuid NOT NULL,\n" +
            "    store_id uuid NOT NULL,\n" +
            "    PRIMARY KEY (cart_id),\n" +
            "    FOREIGN KEY (store_id) REFERENCES store (store_id),\n" +
            "    FOREIGN KEY (user_id) REFERENCES \"user\" (user_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS privilege\n" +
            "(\n" +
            "    privilege_id uuid NOT NULL,\n" +
            "    name text NOT NULL,\n" +
            "    PRIMARY KEY (privilege_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS role\n" +
            "(\n" +
            "    role_id uuid NOT NULL,\n" +
            "    name text NOT NULL,\n" +
            "    PRIMARY KEY (role_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS role_privilege\n" +
            "(\n" +
            "    role_id uuid NOT NULL,\n" +
            "    privilege_id uuid NOT NULL,\n" +
            "    FOREIGN KEY (privilege_id) REFERENCES privilege (privilege_id),\n" +
            "    FOREIGN KEY (role_id) REFERENCES role (role_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS user_role\n" +
            "(\n" +
            "    user_id uuid NOT NULL,\n" +
            "    role_id uuid NOT NULL,\n" +
            "    FOREIGN KEY (role_id) REFERENCES role (role_id),\n" +
            "    FOREIGN KEY (user_id) REFERENCES \"user\" (user_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS product_type\n" +
            "(\n" +
            "    product_type_id uuid NOT NULL,\n" +
            "    type text NOT NULL,\n" +
            "    shipping_required boolean NOT NULL,\n" +
            "    PRIMARY KEY (product_type_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS product\n" +
            "(\n" +
            "    product_id uuid NOT NULL,\n" +
            "    name text NOT NULL,\n" +
            "    description text NOT NULL,\n" +
            "    price decimal NOT NULL,\n" +
            "    tax decimal,\n" +
            "    img uuid,\n" +
            "    recommended boolean NOT NULL,\n" +
            "    product_type_id uuid NOT NULL,\n" +
            "    PRIMARY KEY (product_id),\n" +
            "    FOREIGN KEY (product_type_id) REFERENCES product_type (product_type_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS product_image\n" +
            "(\n" +
            "    product_id uuid NOT NULL,\n" +
            "    image_id uuid NOT NULL,\n" +
            "    FOREIGN KEY (product_id) REFERENCES product (product_id),\n" +
            "    PRIMARY KEY (image_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS cart_product\n" +
            "(\n" +
            "    cart_id uuid NOT NULL,\n" +
            "    product_id uuid NOT NULL,\n" +
            "    FOREIGN KEY (cart_id) REFERENCES cart (cart_id),\n" +
            "    FOREIGN KEY (product_id) REFERENCES product (product_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS product_tag\n" +
            "(\n" +
            "    product_id uuid NOT NULL,\n" +
            "    tag text NOT NULL,\n" +
            "    FOREIGN KEY (product_id) REFERENCES product (product_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS category\n" +
            "(\n" +
            "    category_id uuid NOT NULL,\n" +
            "    name text NOT NULL,\n" +
            "    url text NOT NULL,\n" +
            "    store_id uuid NOT NULL,\n" +
            "    tax decimal,\n" +
            "    PRIMARY KEY (category_id),\n" +
            "    FOREIGN KEY (store_id) REFERENCES store (store_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS category_product\n" +
            "(\n" +
            "    category_id uuid NOT NULL,\n" +
            "    product_id uuid NOT NULL,\n" +
            "    FOREIGN KEY (category_id) REFERENCES category (category_id),\n" +
            "    FOREIGN KEY (product_id) REFERENCES product (product_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS \"order\"\n" +
            "(\n" +
            "    order_id uuid NOT NULL,\n" +
            "    order_number text NOT NULL,\n" +
            "    user_id uuid NOT NULL,\n" +
            "    PRIMARY KEY (order_id),\n" +
            "    FOREIGN KEY (user_id) REFERENCES \"user\" (user_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS order_billing_address\n" +
            "(\n" +
            "    address_id uuid NOT NULL,\n" +
            "    order_id uuid NOT NULL,\n" +
            "    firstname text NOT NULL,\n" +
            "    lastname text NOT NULL,\n" +
            "    line1 text NOT NULL,\n" +
            "    line2 text,\n" +
            "    line3 text,\n" +
            "    city text,\n" +
            "    zip_or_postcode text NOT NULL,\n" +
            "    country_province text NOT NULL,\n" +
            "    country text NOT NULL,\n" +
            "    PRIMARY KEY (address_id),\n" +
            "    FOREIGN KEY (order_id) REFERENCES \"order\" (order_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS order_shipping_address\n" +
            "(\n" +
            "    address_id uuid NOT NULL,\n" +
            "    order_id uuid NOT NULL,\n" +
            "    firstname text NOT NULL,\n" +
            "    lastname text NOT NULL,\n" +
            "    line1 text NOT NULL,\n" +
            "    line2 text,\n" +
            "    line3 text,\n" +
            "    city text NOT NULL,\n" +
            "    zip_or_postcode text NOT NULL,\n" +
            "    country_province text NOT NULL,\n" +
            "    country text NOT NULL,\n" +
            "    PRIMARY KEY (address_id),\n" +
            "    FOREIGN KEY (order_id) REFERENCES \"order\" (order_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS \"orderStatus\"\n" +
            "(\n" +
            "    \"orderStatus_id\" uuid NOT NULL,\n" +
            "    name text NOT NULL,\n" +
            "    \"time\" time NOT NULL,\n" +
            "    order_id uuid NOT NULL,\n" +
            "    PRIMARY KEY (\"orderStatus_id\"),\n" +
            "    FOREIGN KEY (order_id) REFERENCES \"order\" (order_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS \"orderItem\"\n" +
            "(\n" +
            "    \"orderItem_id\" uuid NOT NULL,\n" +
            "    name text NOT NULL,\n" +
            "    description text NOT NULL,\n" +
            "    price decimal NOT NULL,\n" +
            "    tax decimal,\n" +
            "    img uuid,\n" +
            "    \"referenceProduct_id\" uuid,\n" +
            "    order_id uuid NOT NULL,\n" +
            "    FOREIGN KEY (order_id) REFERENCES \"order\" (order_id),\n" +
            "    FOREIGN KEY (\"referenceProduct_id\") REFERENCES product (product_id)\n" +
            ");\n";
    private static final String ADMIN_SQL = "INSERT INTO \"user\"(user_id, user_name, firstname, lastname, email, img, enabled, password, credits) VALUES('3267a194-048f-4888-abff-4b61494fd760', 'Admin', 'System', 'Administrator', 'admin@trainee.com', 'https://cdn.pixabay.com/photo/2016/11/14/17/39/person-1824144_960_720.png', 'TRUE', '$2a$10$3vRkfek8jwbmVFOiHp7aZuVAK1z9HL445eJgskfjo5/EkfqBhLVLe', '0') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO user_shipping_address(address_id, user_id, firstname, lastname, line1, line2, line3, city, zip_or_postcode, country_province, country) VALUES('fd2552ea-77ec-4d52-8362-efa9a058c6ab', '3267a194-048f-4888-abff-4b61494fd760', 'System', 'Administrator', 'Stuttgarter Str. 25', '', '', 'Frankfurt am Main', '60329', 'Hessen', 'Germany') ON CONFLICT DO NOTHING;\n" +
            "UPDATE \"user\" SET default_shipping_address_id = 'fd2552ea-77ec-4d52-8362-efa9a058c6ab' WHERE user_id = '3267a194-048f-4888-abff-4b61494fd760';" +
            "INSERT INTO user_billing_address(address_id, user_id, firstname, lastname, line1, line2, line3, city, zip_or_postcode, country_province, country) VALUES('fd2552ea-77ec-4d52-8362-efa9a058c6ab', '3267a194-048f-4888-abff-4b61494fd760', 'System', 'Administrator', 'Stuttgarter Str. 25', ' ', ' ', 'Frankfurt am Main', '60329', 'Hessen', 'Germany') ON CONFLICT DO NOTHING;\n" +
            "UPDATE \"user\" SET default_billing_address_id = 'fd2552ea-77ec-4d52-8362-efa9a058c6ab' WHERE user_id = '3267a194-048f-4888-abff-4b61494fd760';" +
            "INSERT INTO user_role(user_id, role_id) VALUES('3267a194-048f-4888-abff-4b61494fd760', '0fc7bf52-3d4a-44ac-b755-09be09bffffd') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO cart(cart_id, user_id, store_id) VALUES('34f40e91-b979-4ec1-88bf-94f20cae0f1f', '3267a194-048f-4888-abff-4b61494fd760', 'd2583c4b-7b9a-4b9a-ae0f-19f09989cc9f');";
    private static final String USER_SQL = "INSERT INTO \"user\"(user_id, user_name, firstname, lastname, email, img, enabled, password, credits) VALUES('0f2eed4d-40ba-4686-8d30-17e124b0250a', 'Test', 'Test', 'User', 'test@trainee.com', 'https://cdn.pixabay.com/photo/2016/11/14/17/39/person-1824144_960_720.png', 'TRUE', '$2a$10$3vRkfek8jwbmVFOiHp7aZuVAK1z9HL445eJgskfjo5/EkfqBhLVLe', '0') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO user_shipping_address(address_id, user_id, firstname, lastname, line1, line2, line3, city, zip_or_postcode, country_province, country) VALUES('cccb7465-97ca-45b3-bc55-1d3e71e85178', '0f2eed4d-40ba-4686-8d30-17e124b0250a', 'Test', 'User', 'Stuttgarter Str. 25', '', '', 'Frankfurt am Main', '60329', 'Hessen', 'Germany') ON CONFLICT DO NOTHING;\n" +
            "UPDATE \"user\" SET default_shipping_address_id = 'cccb7465-97ca-45b3-bc55-1d3e71e85178' WHERE user_id = '0f2eed4d-40ba-4686-8d30-17e124b0250a';" +
            "INSERT INTO user_billing_address(address_id, user_id, firstname, lastname, line1, line2, line3, city, zip_or_postcode, country_province, country) VALUES('680ac053-d896-453c-981c-459020efa394', '0f2eed4d-40ba-4686-8d30-17e124b0250a', 'Test', 'User', 'Stuttgarter Str. 25', ' ', ' ', 'Frankfurt am Main', '60329', 'Hessen', 'Germany') ON CONFLICT DO NOTHING;\n" +
            "UPDATE \"user\" SET default_billing_address_id = '680ac053-d896-453c-981c-459020efa394' WHERE user_id = '0f2eed4d-40ba-4686-8d30-17e124b0250a';" +
            "INSERT INTO user_role(user_id, role_id) VALUES('0f2eed4d-40ba-4686-8d30-17e124b0250a', '0b8b2927-30f6-4b5c-b57d-5fe6b1fe4994') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO cart(cart_id, user_id, store_id) VALUES('91dcf041-11cf-418a-8946-04c13d1eafa8', '0f2eed4d-40ba-4686-8d30-17e124b0250a', 'd2583c4b-7b9a-4b9a-ae0f-19f09989cc9f') ON CONFLICT DO NOTHING;";
    private static final String ROLE_USER_SQL = "INSERT INTO role(role_id, name) VALUES('0b8b2927-30f6-4b5c-b57d-5fe6b1fe4994', 'ROLE_USER') ON CONFLICT DO NOTHING;";
    private static final String ROLE_ADMIN_SQL = "INSERT INTO role(role_id, name) VALUES('0fc7bf52-3d4a-44ac-b755-09be09bffffd', 'ROLE_ADMIN') ON CONFLICT DO NOTHING;";
    private static final String ROLES_AND_PRIVILEGES_SQL = "INSERT INTO privilege(privilege_id, name) VALUES('a5d49dcc-9190-4edf-934d-7145b54fbe5a', 'STANDARD_PRIVILEGE') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO privilege(privilege_id, name) VALUES('80867f28-9919-4b18-8e68-f2a4716b6660', 'USERMANAGEMENT_PRIVILEGE') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO privilege(privilege_id, name) VALUES('d77b26a3-3e90-44f9-a61d-4831bcc63535', 'AUTHORITYMANAGEMENT_PRIVILEGE') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO privilege(privilege_id, name) VALUES('9f215b6c-a9fd-4462-a088-d13fa0b368ec', 'STOREMANAGEMENT_PRIVILEGE') ON CONFLICT DO NOTHING;\n" +
            "\n" +
            "INSERT INTO role_privilege(role_id, privilege_id) VALUES('0b8b2927-30f6-4b5c-b57d-5fe6b1fe4994', 'a5d49dcc-9190-4edf-934d-7145b54fbe5a') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO role_privilege(role_id, privilege_id) VALUES('0fc7bf52-3d4a-44ac-b755-09be09bffffd', 'a5d49dcc-9190-4edf-934d-7145b54fbe5a') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO role_privilege(role_id, privilege_id) VALUES('0fc7bf52-3d4a-44ac-b755-09be09bffffd', '80867f28-9919-4b18-8e68-f2a4716b6660') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO role_privilege(role_id, privilege_id) VALUES('0fc7bf52-3d4a-44ac-b755-09be09bffffd', 'd77b26a3-3e90-44f9-a61d-4831bcc63535') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO role_privilege(role_id, privilege_id) VALUES('0fc7bf52-3d4a-44ac-b755-09be09bffffd', '9f215b6c-a9fd-4462-a088-d13fa0b368ec') ON CONFLICT DO NOTHING;";
    private static final String STORE_SETUP_SQL ="INSERT INTO store(store_id, name) VALUES('d2583c4b-7b9a-4b9a-ae0f-19f09989cc9f', 'TheShop') ON CONFLICT DO NOTHING;\n" +
            "\n" +
            "INSERT INTO category(category_id, name, url, store_id) VALUES('2b95aa3d-b431-4e11-b78d-42e6d619e1fa', '1st Category', 'first-category', 'd2583c4b-7b9a-4b9a-ae0f-19f09989cc9f') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO product_type(product_type_id, type, shipping_required) VALUES('6301b6fc-0ac2-4e91-badd-c0f5b782bfc0', 'physical', 'TRUE') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO product_type(product_type_id, type, shipping_required) VALUES('f8062c07-5f90-4cb6-bb1f-42de99313b8a', 'digital', 'FALSE') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO product(product_id, name, description, price, tax, img, product_type_id, recommended) VALUES('f01ca47b-9eae-47c9-be2a-0e4a4d92c04a', 'Lorem ipsum dolor sit amet', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea', '29.99', '15', 'c3786c2d-090a-4c58-becd-cdd18c1a2f37', 'f8062c07-5f90-4cb6-bb1f-42de99313b8a', 'true') ON CONFLICT DO NOTHING;\n"+

            "INSERT INTO product(product_id, name, description, price, tax, img, product_type_id, recommended) VALUES('36f802f5-5798-4813-9a7d-b15b503e7e05', 'Amazon Basics - Ergonomische kabellose Maus - DPI einstellbar - Schwarz ', 'Kompakte kabellose Maus mit einstellbarer DPI-Empfindlichkeit (600, 1000, 1600, 2400 oder 3600 DPI)Moderner Lasersensor, funktioniert auf den meisten Oberflächen, einschließlich Glas; Scrollrad zum Klicken; Vorwärts-/Rückwärts-Daumentasten zur einfachen NavigationKabellose 2.4 GHz-Verbindung mit einer Reichweite von 10 m; verwendet AES-128-Verschlüsselung für zusätzliche Sicherheit; mit kleinem USB-Empfänger, zum Anschluss an Ihren Computer, ohne andere Anschlüsse zu blockierenErgonomische Form und gummierte Seiten für komfortablen Halt; kompatibel mit Windows 7, 8, und 10; benötigt 1 AA Batterie (enthalten); Farben: Schwarz, Rot und Silber Maße: 10,4 x 7,4 x 3,8 cm', '27.99', '15', '250fa197-c3df-4609-a1a9-d960aa215de6', 'f8062c07-5f90-4cb6-bb1f-42de99313b8a', 'false') ON CONFLICT DO NOTHING;\n"+
            "INSERT INTO product(product_id, name, description, price, tax, img, product_type_id, recommended) VALUES('cef65135-86ae-4625-818c-db305e17965c', 'Trust KerbStone', 'Kompakte Funkmaus mit Lasersensor und verstaubarem Mikroempfänger Ultrakleiner USB-Empfänger: einmal anschließen und nie mehr abziehen Hoch präziser Lasersensor für bessere Abtastleistung auf glänzenden Oberflächen 2,4-GHz-Technologie für weiche Bewegungen, einen Funkbereich von 8 Meter und längere Batterielebensdauer Lieferumfang: Kabellose lasermaus, USB-Mikroempfänger, 2x AAA Batterien, Bedienungsanleitung ', '9.99', '15', '37764337-2426-413d-a98e-2884893c5148', 'f8062c07-5f90-4cb6-bb1f-42de99313b8a', 'false');\n"+
            "INSERT INTO product(product_id, name, description, price, tax, img, product_type_id, recommended) VALUES('48e87a4f-d786-4645-b33c-9f680079d8ea', 'Contour UniMouse', 'Superschneller Bildlauf in langen Dokumenten; fortschrittliche SmartShift-Technologie passt den Bildlaufmodus der aktiven Anwendung an Schnelles Umschalten zwischen offenen Dokumenten; Suche auf Tastendruck zum intelligenten und schnellen Suchen im Internet Leistungsstarker Laser für absolute Präzision; Full-Speed USB, kabellose 2,4-GHz-Technologie, wieder aufladbarer Lithium-Ionen-Akku Lieferumfang: Logitech MX Revolution Cordless Laser Mouse, Ladestation, Netzkabel, USB-Mikro-Empfänger, Dokumentation auf Deutsch, CD mit Logitech SetPoint Software Herstellergarantie: 3 Jahre und technischer Support ', '14.99', '15', '0bacfdca-13fb-4230-9685-91ec6047d3bb', 'f8062c07-5f90-4cb6-bb1f-42de99313b8a', 'true') ON CONFLICT DO NOTHING;\n"+

            "INSERT INTO product(product_id, name, description, price, tax, img, product_type_id, recommended) VALUES('c5ff247b-3ab7-4912-aff9-67879a4d1ddb', 'SAMSUNG 24Zoll', 'Bildschirm : 81,28 cm (32\") TFT-LCD IPS Display mit WLED Backlight Auflösung : UHD 4K (3840 x 2160) Signaleingänge : HDMI™, DisplayPort, Thunderbolt 3, USB-Hub 4x USB 3.0 Features:  Neigbar: -5°/22°... ', '29.50', '15', '496b3694-ec20-4a59-adec-9df8a523ce9e', 'f8062c07-5f90-4cb6-bb1f-42de99313b8a', 'false') ON CONFLICT DO NOTHING;\n"+
            "INSERT INTO product(product_id, name, description, price, tax, img, product_type_id, recommended) VALUES('846c809d-00c8-4db9-9195-e17396b2b06f', 'Acer AL1951', 'Bildschirm : 23,8\" (60,45 cm) TFT-LCD IPS Display mit WLED Backlight Auflösung : FullHD (1.920 x 1.080) Signaleingänge : VGA, HDMI, DisplayPort, 1x USB 3.0 Upstream-Port, 4x USB 3.0 Features:  Neigbar: -5°/30°... ', '19.99', '15', 'c5873b60-a264-4b9f-91f6-e8e0a5cf814f', 'f8062c07-5f90-4cb6-bb1f-42de99313b8a', 'false') ON CONFLICT DO NOTHING;\n"+
            "INSERT INTO product(product_id, name, description, price, tax, img, product_type_id, recommended) VALUES('8ac7aae5-5782-4c92-9913-0277a5f14561', 'Lenovo ThinkVision P27u', 'Bildschirm:  68,47 cm (27\") TFT-LCD IPS Display mit WLED Backlight Auflösung: UHD 4K (3840 x 2160) Signaleingänge: HDMI™, DisplayPort, USB 3.0 Type-C  Anschlüsse: 5 x USB 3.0 Features:  Neigbar: -5°/30° (vorne/hinten),... ', '44.99', '15', 'f8bd4a74-3e1f-4ef7-979c-b16ac875b28c', 'f8062c07-5f90-4cb6-bb1f-42de99313b8a', 'true') ON CONFLICT DO NOTHING;\n"+

            "INSERT INTO product(product_id, name, description, price, tax, img, product_type_id, recommended) VALUES('161de387-4a0b-490f-906a-3942b1a9e6a2', 'Quantum powered NASA workbench', 'Sie suchen einen Rechner, der Ihren Ansprüchen gerecht werden kann? Lassen Sie sich vom leistungsstarken Desktop-PC Slim Desktop S01-aF0300ng von HP überzeugen. ', '9999.99', '15', 'fe6d6191-b083-464c-996b-21460025bc20', 'f8062c07-5f90-4cb6-bb1f-42de99313b8a', 'false') ON CONFLICT DO NOTHING;\n"+
            "INSERT INTO product(product_id, name, description, price, tax, img, product_type_id, recommended) VALUES('2ad2058f-4e9e-4dcf-bf8d-e5ba9227f01f', 'Uniys MPI 4663', 'Die Festplatte vom Typ SSD, die in dem Gerät verbaut ist, sorgt für eine schnelle Performance bei allen Anwendungen. Mit einer Festplattenkapazität von 256 GB bietet das Gerät ausreichend Platz für Dokumente, Bilder und weitere Medien. ', '21.01', '15', '2103aa1b-79dc-4938-a354-07f44aa356f7', 'f8062c07-5f90-4cb6-bb1f-42de99313b8a', 'false') ON CONFLICT DO NOTHING;\n"+
            "INSERT INTO product(product_id, name, description, price, tax, img, product_type_id, recommended) VALUES('c0159d3f-f822-4233-9880-cb8f42e42b9e', 'Some random Mac', 'Apple bad', '0.99', '15', 'cbcac95a-b09b-430e-b919-d7ab306d1790', 'f8062c07-5f90-4cb6-bb1f-42de99313b8a', 'true') ON CONFLICT DO NOTHING;";

    private static final String STORE_SETUP_SQL2 = "INSERT INTO category_product(category_id, product_id) VALUES('2b95aa3d-b431-4e11-b78d-42e6d619e1fa', 'f01ca47b-9eae-47c9-be2a-0e4a4d92c04a') ON CONFLICT DO NOTHING;\n";
    private static final String STORE_SETUP_SQL3 = "INSERT INTO product_tag(product_id, tag) VALUES('f01ca47b-9eae-47c9-be2a-0e4a4d92c04a', 'computer') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO product_tag(product_id, tag) VALUES('f01ca47b-9eae-47c9-be2a-0e4a4d92c04a', 'electronic') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO product_tag(product_id, tag) VALUES('f01ca47b-9eae-47c9-be2a-0e4a4d92c04a', 'power') ON CONFLICT DO NOTHING;\n" +
            "INSERT INTO product_tag(product_id, tag) VALUES('f01ca47b-9eae-47c9-be2a-0e4a4d92c04a', 'monitor') ON CONFLICT DO NOTHING;\n";
    public void initTables() {
        jdbcTemplate.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public;");
        jdbcTemplate.execute(TABLE_SQL);
        if(!roleDAO.roleExists(UUID.fromString("0fc7bf52-3d4a-44ac-b755-09be09bffffd")))
            jdbcTemplate.execute(ROLE_ADMIN_SQL);
        if(!roleDAO.roleExists(UUID.fromString("e32b30a6-8644-4db1-8215-d939464131c1")))
            jdbcTemplate.execute(ROLE_USER_SQL);
        if(!privilegeDAO.privilegeExists(UUID.fromString("a5d49dcc-9190-4edf-934d-7145b54fbe5a")))
            jdbcTemplate.execute(ROLES_AND_PRIVILEGES_SQL);
        if(!storeDAO.storeExists(UUID.fromString("d2583c4b-7b9a-4b9a-ae0f-19f09989cc9f"))){
            jdbcTemplate.execute(STORE_SETUP_SQL);
            jdbcTemplate.execute(STORE_SETUP_SQL2);
            jdbcTemplate.execute(STORE_SETUP_SQL3);
        }
        if(!userDAO.nameExists("Admin"))
            jdbcTemplate.execute(ADMIN_SQL);
        if(!userDAO.nameExists("Test"))
            jdbcTemplate.execute(USER_SQL);
    }
}
