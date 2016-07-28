/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jboxes.tests.core;

import com.io7m.jboxes.core.Box;
import com.io7m.jboxes.core.BoxMutable;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.valid4j.errors.RequireViolation;

public final class BoxMutableTest
{
  @Rule public ExpectedException expected = ExpectedException.none();

  @Test
  public void testNegativeWidth()
  {
    this.expected.expect(RequireViolation.class);
    BoxMutable.create(0, -1, 0, 0).checkPreconditions();
  }

  @Test
  public void testNegativeHeight()
  {
    this.expected.expect(RequireViolation.class);
    BoxMutable.create(0, 0, 0, -1).checkPreconditions();
  }

  @Test
  public void testIdentities()
  {
    final BoxMutable<Object> b0 = BoxMutable.create(0, 2, 4, 6);
    Assert.assertEquals(0L, (long) b0.minimumX());
    Assert.assertEquals(2L, (long) b0.maximumX());
    Assert.assertEquals(4L, (long) b0.minimumY());
    Assert.assertEquals(6L, (long) b0.maximumY());

    final BoxMutable<Object> b1 = BoxMutable.create().from(b0);
    Assert.assertEquals(b0, b1);
    Assert.assertEquals(b1, b0);

    Assert.assertEquals((long) b0.hashCode(), (long) b1.hashCode());
    Assert.assertEquals(b0.toString(), b1.toString());

    final Box<Object> bi0 = b0.toImmutable();
    final Box<Object> bi1 = b1.toImmutable();
    Assert.assertEquals(bi0, bi1);
  }

  @Test
  public void testUninitializedMinimumX()
  {
    final BoxMutable<Object> b0 = BoxMutable.create();
    this.expected.expect(IllegalStateException.class);
    b0.minimumX();
  }

  @Test
  public void testUninitializedMinimumY()
  {
    final BoxMutable<Object> b0 = BoxMutable.create();
    this.expected.expect(IllegalStateException.class);
    b0.minimumY();
  }

  @Test
  public void testUninitializedMaximumX()
  {
    final BoxMutable<Object> b0 = BoxMutable.create();
    this.expected.expect(IllegalStateException.class);
    b0.maximumX();
  }

  @Test
  public void testUninitializedMaximumY()
  {
    final BoxMutable<Object> b0 = BoxMutable.create();
    this.expected.expect(IllegalStateException.class);
    b0.maximumY();
  }

  @Test
  public void testClear()
  {
    final BoxMutable<Object> b0 = BoxMutable.create(0, 2, 4, 6);
    final BoxMutable<Object> b1 = BoxMutable.create(0, 2, 4, 6);

    b0.clear();

    Assert.assertNotEquals(b0, b1);
    Assert.assertFalse(b0.isInitialized());
  }

  @Test
  public void testToString()
  {
    final BoxMutable<Object> b0 = BoxMutable.create(0, 2, 4, 6);

    {
      final BoxMutable<Object> b1 = BoxMutable.create().from(b0);
      b1.setMaximumX(b1.maximumX() + 1);
      Assert.assertNotEquals(b0.toString(), b1.toString());
    }

    {
      final BoxMutable<Object> b1 = BoxMutable.create().from(b0);
      b1.setMaximumY(b1.maximumY() + 1);
      Assert.assertNotEquals(b0.toString(), b1.toString());
    }

    {
      final BoxMutable<Object> b1 = BoxMutable.create().from(b0);
      b1.setMinimumX(b1.minimumX() + 1);
      Assert.assertNotEquals(b0.toString(), b1.toString());
    }

    {
      final BoxMutable<Object> b1 = BoxMutable.create().from(b0);
      b1.setMinimumY(b1.minimumY() + 1);
      Assert.assertNotEquals(b0.toString(), b1.toString());
    }
  }

}
