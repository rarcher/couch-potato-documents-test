/*
 * Copyright 2014-2016 Ryan Archer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package codes.soloware.couchpotato.server.sound.javax;

import javax.sound.sampled.CompoundControl;
import javax.sound.sampled.Control;
/**
 * A human-readable <code>CharSequence</code> describing a <code>Control</code> in detail.
 */
public class ControlDescription implements CharSequence
{
	private static final int defaultIndent=0;
	private final Control subject;
	private final int indent;
	private String contents;

	public ControlDescription(final Control subject)
	{
		this(subject, defaultIndent);
	}

	public ControlDescription(final Control subject, final int indent)
	{
		if (subject==null)
			throw new NullPointerException("Given control to describe is null.");
		if (indent<0)
			throw new IllegalArgumentException("Indent setting is negative. More specifically, it is "+indent+".");
		this.subject=subject;
		this.indent=indent;
		contents=null;
	}

	@Override
	public char charAt(final int index)
	{
		return toString().charAt(index);
	}

	@Override
	public int length()
	{
		return toString().length();
	}

	@Override
	public CharSequence subSequence(final int start, final int end)
	{
		return toString().subSequence(start, end);
	}

	@Override
	public final String toString()
	{
		if (contents==null)
		{
			final StringBuilder contentBuilder=new StringBuilder();

			for (int loop=0; loop<indent; loop++)
				contentBuilder.append('\t');
			contentBuilder.append(subject.toString());

			if (subject instanceof CompoundControl)
			{
				for (final Control member : ((CompoundControl)subject).getMemberControls())
				{
					contentBuilder.append(System.getProperty("line.separator"));
					contentBuilder.append(new ControlDescription(member, indent+1).toString());
				}
			}

			contents=contentBuilder.toString();
		}
		return contents;
	}
}
