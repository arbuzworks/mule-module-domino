/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.domino.transformers;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.mule.api.transformer.Transformer;
import org.mule.module.domino.jaxb.Datetime;
import org.mule.module.domino.jaxb.Document;
import org.mule.module.domino.jaxb.Font;
import org.mule.module.domino.jaxb.Item;
import org.mule.module.domino.jaxb.Par;
import org.mule.module.domino.jaxb.Pardef;
import org.mule.module.domino.jaxb.Parstyle;
import org.mule.module.domino.jaxb.Richtext;
import org.mule.module.domino.jaxb.Text;
import org.mule.transformer.AbstractTransformerTestCase;

public class DocumentToDxlTestCase extends AbstractTransformerTestCase {

	@Override
	public Transformer getTransformer() throws Exception {
		DocumentToDxl trans = new DocumentToDxl();
		initialiseObject(trans);
		return trans;
	}

	@Override
	public Transformer getRoundTripTransformer() throws Exception {
		DxlToDocument trans = new DxlToDocument();
		initialiseObject(trans);
		return trans;
	}

	@Override
	public Object getTestData() {
		Document document = new Document();
		
		Item item1 = new Item();
		item1.setName("Test date time");
		Datetime d = new Datetime();
		d.setValue("20110808T114712,61-04");
		item1.setDatetime(d);
		document.getItem().add(item1);
		
		Item item2 = new Item();
		item2.setName("Subject");
		Text t = new Text();
		t.getContent().add("Test Subject");
		item2.setText(t);
		document.getItem().add(item2);
		
		Item item3 = new Item();
		item3.setName("Test rich text");


		Richtext r = new Richtext();
		Pardef pd = new Pardef();
		pd.setId("1");
		Parstyle ps = new Parstyle();
		Font font = new Font();
		font.setSize("16");
		ps.setFont(font);
		pd.setParstyle(ps);
		Par par = new Par();
		par.setDef("1");
		par.getContent().add("RICH TEXT");
		r.getParOrPardefOrTable().add(pd);
		r.getParOrPardefOrTable().add(par);
		item3.setRichtext(r);
		document.getItem().add(item3);
		
		return document;
	}
	
	@Override
	public Object getResultData() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><document xmlns=\"http://www.lotus.com/dxl\"><item name=\"Test date time\"><datetime>20110808T114712,61-04</datetime></item><item name=\"Subject\"><text>Test Subject</text></item><item name=\"Test rich text\"><richtext><pardef id=\"1\"><parstyle><font size=\"16\"/></parstyle></pardef><par def=\"1\">RICH TEXT</par></richtext></item></document>";
	}

	public boolean compareRoundtripResults(Object expected, Object result) {
		Document doc1 = (Document)expected;
		Document doc2 = (Document)result;

		String str1 = documentToXML(doc1);
		String str2 = documentToXML(doc2);
		
		assertEquals(str1, str2);
		return compareResults(str1, str2);
	}
	
	private String documentToXML(Document document) {

		Marshaller m;
		try {
			JAXBContext jc = JAXBContext
					.newInstance("org.mule.module.domino.jaxb");
			m = jc.createMarshaller();
			StringWriter sw = new StringWriter();
			m.marshal(new JAXBElement<Document>(new QName(
					"http://www.lotus.com/dxl", "document"), Document.class,
					document), sw);
			return sw.toString();
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			fail();
		}
		return null;
	}
	
    public void testTransform() throws Exception
    {
        Transformer trans = this.getTransformer();
        Object result = trans.transform(getTestData());
        assertNotNull("The result of the transform shouldn't be null", result);
System.out.println("result: " + result);
        Object expectedResult = this.getResultData();
        assertNotNull("The expected result data must not be null", expectedResult);
        System.out.println("expectedResult: " + expectedResult);
        assertTrue("Transformation result does not match expected result", this.compareResults(
            expectedResult, result));
    }
}
