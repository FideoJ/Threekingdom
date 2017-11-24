package com.hp.hpl.sparta;

/** This interface defines the SAX Parser interface for Sparta.
 It is used by BuildDocument, the DOM Parser and by externel
 SAX Parser adapters.

 <blockquote><small> Copyright (C) 2002 Hewlett-Packard Company.
 This file is part of Sparta, an XML Parser, DOM, and XPath library.
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public License
 as published by the Free Software Foundation; either version 2.1 of
 the License, or (at your option) any later version.  This library
 is distributed in the hope that it will be useful, but WITHOUT ANY
 WARRANTY; without even the implied warranty of MERCHANTABILITY or
 FITNESS FOR A PARTICULAR PURPOSE. </small></blockquote>
 @see <a "href="doc-files/LGPL.txt">GNU Lesser General Public License</a>
 @version  $Date: 2002/08/19 05:03:57 $  $Revision: 1.1.1.1 $
 @author Sergio Marti
 */

public interface ParseHandler {

  void setParseSource(ParseSource ps);

  ParseSource getParseSource();

  void startDocument() throws ParseException;

  void endDocument() throws ParseException;

  void startElement(Element element) throws ParseException;

  void endElement(Element element) throws ParseException;

  void characters(char[] buf, int offset, int len) throws ParseException;

}

// $Log: ParseHandler.java,v $
// Revision 1.1.1.1  2002/08/19 05:03:57  eobrain
// import from HP Labs internal CVS
//
// Revision 1.4  2002/08/18 04:39:37  eob
// Add copyright and other formatting and commenting in preparation for
// release to SourceForge.
//
// Revision 1.3  2002/08/17 00:54:14  sermarti
//
// Revision 1.2  2002/08/01 23:29:17  sermarti
// Much faster Sparta parsing.
// Has debug features enabled by default. Currently toggled
// in ParseCharStream.java and recompiled.
//
// Revision 1.1  2002/07/25 21:10:15  sermarti
// Adding files that mysteriously weren't added from Sparta before.
