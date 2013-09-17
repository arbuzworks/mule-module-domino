/*
 * $Id: TransformUtil.java es@mindsinsight.com $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MindsInsight Inc.  All rights reserved.  http://www.mindsinsight.com/
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.domino.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.NotesException;

/**
 * <code>TransformUtil</code>
 */
public class TransformUtil {

    public static void mapToDocument(Database database, Map<String,Object> source, Document target) throws NotesException
    {

        for (Map.Entry<String, Object> item:source.entrySet())
        {
            String key = item.getKey();
            Object value = item.getValue();

            //TODO All Domino Specific types must be mapped to standard Java types
            // (DateRange, Registration, RichTextParagraphStyle, RichTextStyle, Stream
            if (value instanceof Date)
            {
                value = database.getParent().createDateTime((Date)value);
            }
            else if (value instanceof Calendar)
            {
                value = database.getParent().createDateTime((Calendar)value);
            }
            
            if (target.getItemValue(key) == null)
				target.appendItemValue(key, value);
			else
				target.replaceItemValue(key, value);
        }
    }
}
