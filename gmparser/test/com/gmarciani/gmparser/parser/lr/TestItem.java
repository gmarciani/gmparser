/*	The MIT License (MIT)
 *
 *	Copyright (c) 2014 Giacomo Marciani
 *	
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *	SOFTWARE.
*/

package com.gmarciani.gmparser.parser.lr;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.commons.nple.Pair;
import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.parser.lr.bigproduction.Item;

public class TestItem {
	
	private final static Production PRODUCTION = new Production("S", "aSB");
	private final static Production EPSILON_PRODUCTION = new Production("S", Grammar.EPSILON);

	@Test public void generateItems() {
		GSet<Item> items = Item.generateItems(PRODUCTION);
		
		GSet<Item> expectedItems = new GSet<Item>();
		expectedItems.add(new Item(PRODUCTION, 0));
		expectedItems.add(new Item(PRODUCTION, 1));
		expectedItems.add(new Item(PRODUCTION, 2));
		expectedItems.add(new Item(PRODUCTION, 3));
		
		assertEquals("Uncorrect items generation", expectedItems, items);
	}
	
	@Test public void generateItemsWithEpsilonProduction() {
		GSet<Item> items = Item.generateItems(EPSILON_PRODUCTION);
		
		GSet<Item> expectedItems = new GSet<Item>();
		expectedItems.add(new Item(EPSILON_PRODUCTION, 1));
		
		assertEquals("Uncorrect items generation", expectedItems, items);
	}
	
	@Test public void generateItemsPair() {
		GSet<Pair<Item, Item>> pairs = Item.generateItemPair(PRODUCTION);
		
		GSet<Pair<Item, Item>> expectedPairs = new GSet<Pair<Item, Item>>();
		expectedPairs.add(new Pair<Item, Item>(new Item(PRODUCTION, 0), new Item(PRODUCTION, 1)));
		expectedPairs.add(new Pair<Item, Item>(new Item(PRODUCTION, 1), new Item(PRODUCTION, 2)));
		expectedPairs.add(new Pair<Item, Item>(new Item(PRODUCTION, 2), new Item(PRODUCTION, 3)));
		
		assertEquals("Uncorrect items pairs generation", expectedPairs, pairs);
	}
	
	@Test public void justReadNextSymbol() {
		GSet<Item> items = Item.generateItems(PRODUCTION);
		
		for (Item item : items) {
			System.out.println(item + " just read: " + item.getJustReadCharacter() + "; next: " + item.getNextCharacter());
		}
	}
	
	@Test public void isComplete() {
		Item completeItem = new Item("S", "aSB", 3);
		Item nonCompleteItem = new Item("S", "aSB", 2);
		
		assertTrue("Uncorrect completeness check. Should be complete: " + completeItem, completeItem.isComplete());
		assertFalse("Uncorrect completeness check. Should not be complete: " + nonCompleteItem, nonCompleteItem.isComplete());
	}

}
