package com.github.davidmoten.rtree2.geometry;

import org.junit.Test;

import static org.junit.Assert.*;

public final class PolygonTest {

    private static final double PRECISION = 0.00001;
    private static final double[] SIMPLE_SQUARE = {-1, -1, -1, 1, 1, 1, 1, -1};
    private static final double[] SIMPLE_SQUARE_CLOSED = {-1, -1, -1, 1, 1, 1, 1, -1, -1, -1};
    private static final double[] SIMPLE_SQUARE_DUPLICATES = {-3, 0, -3, 0, -3, 0, 1, 4, 2, -5, -2, -10, -2, -10};

    @Test
    public void testDoesIntersectHorizontalLine() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Line b = Geometries.line(-2, 0, 2, 0);
        assertTrue(a.intersects(b));
    }

    @Test
    public void testDoesIntersectVerticalLine() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Line b = Geometries.line(0.5, -5.0, 0.5, 10.1);
        assertTrue(a.intersects(b));
    }

    @Test
    public void testDoesIntersectArbitraryLine() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Line b = Geometries.line(-1.2, 5.0, 0.5, -2.5);
        assertTrue(a.intersects(b));
    }

    @Test
    public void testDoesIntersectArbitraryLineClosedPolygon() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE_CLOSED);
        Line b = Geometries.line(-1.2, 5.0, 0.5, -2.5);
        assertTrue(a.intersects(b));
    }

    @Test
    public void testDoesIntersectContainedLine() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Line b = Geometries.line(-0.2, 0.5, 0.2, -0.5);
        assertTrue(a.intersects(b));
    }

    @Test
    public void testDoesNotIntersectHorizontalLine() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Line b = Geometries.line(-0.5, 5, 0.5, 5);
        assertFalse(a.intersects(b));
    }

    @Test
    public void testDoesNotIntersectVerticalLine() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Line b = Geometries.line(-4, 0, -4, 5);
        assertFalse(a.intersects(b));
    }

    @Test
    public void testDoesNotIntersectArbitraryLine() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Line b = Geometries.line(0.1, 2.2, 10.7, 3.1);
        assertFalse(a.intersects(b));
    }

    @Test
    public void testLineIsNotInfinite() {
        // Check that line is treated like a segment rather than an infinite line
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Line b = Geometries.line(0.5, 5.0, 0.5, 10.1);
        assertFalse(a.intersects(b));
    }

    @Test
    public void testDoesIntersectPoint() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE_DUPLICATES);
        Point b = Geometries.point(0.5, 1.2);
        assertTrue(a.intersects(b));
    }

    @Test
    public void testEdgeDoesTouchPoint() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Point b = Geometries.point(0.5, 1.0);
        assertTrue(a.intersects(b));
    }

    @Test
    public void testDoesNotIntersectPoint() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE_DUPLICATES);
        Point b = Geometries.point(-2.5, 3.2);
        assertFalse(a.intersects(b));
    }

    @Test
    public void testDoesIntersectPolygon() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Polygon b = Geometries.polygon(new double[]{0.5, 0.5, 2, 0.5, 2, 2, 0.5, 2});
        assertTrue(a.intersects(b));
    }

    @Test
    public void testDoesNotIntersectPolygon() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Polygon b = Geometries.polygon(new double[]{1.5, 1.5, 2, 1.5, 2, 2, 1.5, 2});
        assertFalse(a.intersects(b));
    }

    @Test
    public void testDoesIntersectRectangle() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Rectangle b = Geometries.rectangle(0.5, 0.5, 2, 2);
        assertTrue(a.intersects(b));
    }

    @Test
    public void testDoesNotIntersectRectangle() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Rectangle b = Geometries.rectangle(1.5, 1.5, 2, 2);
        assertFalse(a.intersects(b));
    }

    @Test
    public void testRectangleZeroDistance() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Rectangle b = Geometries.rectangle(0.5, 0.5, 2, 2);
        assertEquals(0.0, a.distance(b), PRECISION);
    }

    @Test
    public void testRectangleNonZeroDistance() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE);
        Rectangle b = Geometries.rectangle(1.5, 1.5, 2, 2);
        assertEquals(0.70710678, a.distance(b), PRECISION);
    }

    @Test
    public void testPolygonMbr() {
        Polygon a = Geometries.polygon(SIMPLE_SQUARE_DUPLICATES);
        Rectangle mbr = a.mbr();
        assertEquals(-3, mbr.x1(), PRECISION);
        assertEquals(-10, mbr.y1(), PRECISION);
        assertEquals(2, mbr.x2(), PRECISION);
        assertEquals(4, mbr.y2(), PRECISION);
    }

    @Test
    public void testConvexPolygonLimitation() {
        assertThrows(UnsupportedOperationException.class, () ->
                Geometries.polygon(new double[] {0, 0, 0, 1, 0.5, 0.5, 1, 1, 1, 0}));
    }
}
