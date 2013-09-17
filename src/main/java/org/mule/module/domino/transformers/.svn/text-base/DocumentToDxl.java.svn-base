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
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.module.domino.jaxb.Document;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.types.DataTypeFactory;

/**
 * Transformer to convert Document object to DXL string
 */
public class DocumentToDxl extends AbstractMessageTransformer implements DiscoverableTransformer {

	private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

	/**
	 * Get a priority weighting
	 * @return
	 */
	public int getPriorityWeighting() {
		return weighting;
	}

	/**
	 * Specify the priority weighting
	 * @param weighting
	 */
	public void setPriorityWeighting(int weighting) {
		this.weighting = weighting;
	}
	
	public DocumentToDxl() {
		registerSourceType(DataTypeFactory.create(Document.class));
		setReturnDataType(DataTypeFactory.TEXT_STRING);
		setName(DocumentToDxl.class.getSimpleName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		String dxl = null;
		try {
			Document document = (Document) message.getPayload();

			JAXBContext jc = JAXBContext
					.newInstance("org.mule.module.domino.jaxb");
			Marshaller m = jc.createMarshaller();
			StringWriter sw = new StringWriter();
			m.marshal(new JAXBElement<Document>(new QName("http://www.lotus.com/dxl", "document"),
					Document.class, document), sw);
			dxl = sw.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TransformerException(this, e);
		}
		return dxl;
	}
}
