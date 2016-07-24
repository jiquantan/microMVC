package org.microMVC.web.aop;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**   
* @Description: 主要负责解析xml配置的各种通知类型
* @author tanjq
* @version V1.0   
*/
public class BeanParserWithAOP {
	private Map<String, AopAspect> aopAspectMap = new HashMap<String, AopAspect>();

	public void init(String configFile) throws DocumentException {
		SAXReader reader = new SAXReader();

		File file = new File(configFile);
		Document document = reader.read(file);
		Element root = document.getRootElement().element("aop");
		if (root == null)
			return;
		for (Iterator<?> i = root.elementIterator("aspect"); i.hasNext();) {
			AopAspect aopAspect = new AopAspect();
			Element beanNode = (Element) i.next();
			String aspectID = getAttri(beanNode, "id");
			String aspcetClasses = getAttri(beanNode, "classes");
			String aspcetMethod = getAttri(beanNode, "method");

			Element beforeAdviceElement = beanNode.element("before");
			Element afterAdviceElement = beanNode.element("after");
			Element aroundAdviceElement = beanNode.element("around");
			String beforeAdvice = getAttri(beforeAdviceElement, "bean-ref");
			String afterAdvice = getAttri(afterAdviceElement, "bean-ref");
			String aroundAdvice = getAttri(aroundAdviceElement, "bean-ref");

			aopAspect.setId(aspectID);
			aopAspect.setMethod(aspcetMethod);
			aopAspect.setClasses(aspcetClasses);
			aopAspect.setBeforeAdvice(beforeAdvice);
			aopAspect.setAfterAdvice(afterAdvice);
			aopAspect.setAroundAdvice(aroundAdvice);

			aopAspectMap.put(aspectID, aopAspect);
		}
	}

	private String getAttri(Element node, String attriName) {
		String value = null;
		if (node != null && node.attribute(attriName) != null) {
			value = node.attribute(attriName).getText();
		}
		return value;
	}

	public Map<String, AopAspect> getAopAspectMap() {
		return aopAspectMap;
	}
}
