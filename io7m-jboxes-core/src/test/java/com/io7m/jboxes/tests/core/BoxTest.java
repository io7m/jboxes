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

public final class BoxTest
{
  @Rule public ExpectedException expected = ExpectedException.none();

  @Test
  public void testNegativeWidth()
  {
    this.expected.expect(RequireViolation.class);
    Box.of(0, -1, 0, 0);
  }

  @Test
  public void testNegativeHeight()
  {
    this.expected.expect(RequireViolation.class);
    Box.of(0, 0, 0, -1);
  }

  @Test
  public void testMutable()
  {
    final BoxMutable<Object> b0 = BoxMutable.create(0, 2, 4, 6);
    final Box<Object> b1 = Box.copyOf(b0);
    final Box<Object> b2 = Box.of(0, 2, 4, 6);
    Assert.assertEquals(b2, b1);
  }

  @Test
  public void testIdentities()
  {
    final Box<Object> b0 = Box.of(0, 2, 4, 6);
    Assert.assertEquals(0L, (long) b0.minimumX());
    Assert.assertEquals(2L, (long) b0.maximumX());
    Assert.assertEquals(4L, (long) b0.minimumY());
    Assert.assertEquals(6L, (long) b0.maximumY());

    final Box<Object> b1 = Box.copyOf(b0);
    Assert.assertEquals(b0, b1);
    Assert.assertEquals(b1, b0);

    Assert.assertEquals(-1L, (long) b0.withMinimumX(-1).minimumX());
    Assert.assertEquals(-1L, (long) b0.withMinimumY(-1).minimumY());
    Assert.assertEquals(10L, (long) b0.withMaximumX(10).maximumX());
    Assert.assertEquals(10L, (long) b0.withMaximumY(10).maximumY());

    Assert.assertEquals(b0, b0.withMaximumX(b0.maximumX()));
    Assert.assertEquals(b0, b0.withMaximumY(b0.maximumY()));
    Assert.assertEquals(b0, b0.withMinimumY(b0.minimumY()));
    Assert.assertEquals(b0, b0.withMinimumX(b0.minimumX()));

    Assert.assertEquals(b0, Box.builder().from(b0).build());
  }

  @Test
  public void testBuilder0()
  {
    final Box.Builder<Object> b = Box.builder();
    this.expected.expect(IllegalStateException.class);
    b.build();
  }

  @Test
  public void testBuilder1()
  {
    final Box.Builder<Object> b = Box.builder();
    b.setMaximumX(1);
    this.expected.expect(IllegalStateException.class);
    b.build();
  }

  @Test
  public void testBuilder2()
  {
    final Box.Builder<Object> b = Box.builder();
    b.setMaximumX(1);
    b.setMaximumY(2);
    this.expected.expect(IllegalStateException.class);
    b.build();
  }

  @Test
  public void testBuilder3()
  {
    final Box.Builder<Object> b = Box.builder();
    b.setMinimumX(0);
    b.setMaximumX(1);
    b.setMaximumY(2);
    this.expected.expect(IllegalStateException.class);
    b.build();
  }

  @Test
  public void testBuilder4()
  {
    final Box.Builder<Object> b = Box.builder();
    b.setMinimumY(0);
    b.setMaximumX(1);
    b.setMaximumY(2);
    this.expected.expect(IllegalStateException.class);
    b.build();
  }

  @Test
  public void testBuilder()
  {
    final Box.Builder<Object> b = Box.builder();
    b.setMinimumX(0);
    b.setMinimumY(0);
    b.setMaximumX(1);
    b.setMaximumY(2);
    final Box<Object> b0 = b.build();

    Assert.assertEquals(0L, (long) b0.minimumX());
    Assert.assertEquals(1L, (long) b0.maximumX());
    Assert.assertEquals(0L, (long) b0.minimumY());
    Assert.assertEquals(2L, (long) b0.maximumY());
  }

  @Test
  public void testToString()
  {
    final Box<Object> b0 = Box.of(0, 2, 4, 6);

    Assert.assertNotEquals(
      b0.toString(), b0.withMaximumX(b0.maximumX() + 1).toString());
    Assert.assertNotEquals(
      b0.toString(), b0.withMaximumY(b0.maximumY() + 1).toString());

    Assert.assertNotEquals(
      b0.toString(), b0.withMinimumX(b0.minimumX() - 1).toString());
    Assert.assertNotEquals(
      b0.toString(), b0.withMinimumY(b0.minimumY() - 1).toString());
  }

}
