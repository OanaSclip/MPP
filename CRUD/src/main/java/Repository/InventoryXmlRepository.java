package Repository;

import Domain.Inventory;
import Exceptions.ValidatorException;
import Validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryXmlRepository extends InMemoRepo<Integer, Inventory>   {
    private String filename;

    public InventoryXmlRepository(Validator<Inventory> validator, String filename) throws Exception {
        super(validator);
        this.filename = filename;
        loadData();
    }
    private static String getTextByTagName(Element element, String tag){
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }
    private void loadData() throws Exception {
        List<Inventory> inventories = new ArrayList<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Inventory.xml");
        Element root = xmlDoc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength();i++){
            Node child=childNodes.item(i);
            if(child instanceof Element){
                Element inventoryElement=(Element) child;
                String id=inventoryElement.getAttribute("id");

                String idClient=getTextByTagName(inventoryElement,"idClient");
                String idBook=getTextByTagName(inventoryElement,"idBook");

                Inventory inventory=new Inventory(Integer.parseInt(id),Integer.parseInt(idClient),Integer.parseInt(idBook));
                inventories.add(inventory);
            }
        }

        for (Inventory inventory : inventories) {
            try {
                super.save(inventory);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<Inventory> findOne(Integer integer) {
        Optional<Inventory> optional = super.findOne(integer);
        if (optional.isPresent()) {
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Inventory> findAll() {
        return super.findAll();
    }

    private static void appendChildToElement(Document doc, Element parent, String tag, String value){
        Element element = doc.createElement(tag);
        element.setTextContent(value);
        parent.appendChild(element);
    }
    public Optional<Inventory> save(Inventory inventory) throws Exception,ValidatorException{
        Optional<Inventory> optional = super.save(inventory);
        if (optional.isPresent()) {
            return optional;
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Inventory.xml");
        Element root = xmlDoc.getDocumentElement();

        Element ourInventory=xmlDoc.createElement("inventory");
        ourInventory.setAttribute("id", String.valueOf(inventory.getIdEntity()));
        appendChildToElement(xmlDoc,ourInventory,"idClient", String.valueOf(inventory.getIdClient()));
        appendChildToElement(xmlDoc,ourInventory,"idBook", String.valueOf(inventory.getIdBook()));

        root.appendChild(ourInventory);
        Transformer transformer= TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root),new StreamResult("./Data/Inventory.xml"));

        return Optional.empty();
    }
    @Override
    public Optional<Inventory> delete(Integer id) throws Exception{
        Optional<Inventory> optional = super.delete(id);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Inventory.xml");
        Element root = xmlDoc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength();i++){
            Node child=childNodes.item(i);
            if(child instanceof Element){
                Element inventoryElement=(Element) child;
                String idInventory=inventoryElement.getAttribute("id");
                if(Integer.parseInt(idInventory)==id){
                    root.removeChild(child);
                }
            }
        }
        Transformer transformer= TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root),new StreamResult("./Data/Inventory.xml"));

        return Optional.empty();
    }
    private static void replaceNode(Document doc,Node old, Element parent, String tag, String value){
        Element element = doc.createElement(tag);
        element.setTextContent(value);
        parent.replaceChild(element,old);
    }

    private static Node old(Element element, String tag){
        return element.getElementsByTagName(tag).item(0);
    }

    @Override
    public Optional<Inventory> update(Inventory entity) throws ValidatorException,Exception {
        Optional<Inventory> optional= super.update(entity);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Inventory.xml");
        Element root = xmlDoc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength();i++){
            Node child=childNodes.item(i);
            if(child instanceof Element){
                Element inventoryElement=(Element) child;
                String idInventory=inventoryElement.getAttribute("id");
                if(Integer.parseInt(idInventory)==entity.getIdEntity()){
                    Node auth=old(inventoryElement,"idClient");
                    replaceNode(xmlDoc,auth,inventoryElement,"idClient", String.valueOf(entity.getIdClient()));
                    Node titl=old(inventoryElement,"idBook");
                    replaceNode(xmlDoc,titl,inventoryElement,"idBook", String.valueOf(entity.getIdBook()));
                }
            }
        }

        Transformer transformer= TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root),new StreamResult("./Data/Inventory.xml"));

        return Optional.empty();
    }

}
