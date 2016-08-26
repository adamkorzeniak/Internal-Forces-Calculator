package com.github.adkorzen.InternalForcesCalculator.tests.math;

import static org.junit.Assert.*;
import static com.github.adkorzen.InternalForcesCalculator.math.Math.*;


import org.junit.Test;

import com.github.adkorzen.InternalForcesCalculator.elements.Project;

public class MathTest {

	@Test
	public void DegreeToTangent_ZeroDegree_TangentZero(){
		double actual = degreeToTangent(0);
		
		assertEquals(0, actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_180Degree_TangentZero(){
		double actual = degreeToTangent(180);
		
		assertEquals(0, actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_Minus180Degree_TangentZero(){
		double actual = degreeToTangent(-180);
		
		assertEquals(0, actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_1080Degree_TangentZero(){
		double actual = degreeToTangent(1080);
		
		assertEquals(0, actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_90Degree_TangentInfinity(){
		double actual = degreeToTangent(90);
		
		assertEquals(Double.POSITIVE_INFINITY, actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_Minus90Degree_TangentInfinity(){
		double actual = degreeToTangent(-90);
		
		assertEquals(Double.POSITIVE_INFINITY, actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_15Degree_CorrectValue(){
		double actual = degreeToTangent(15);
		
		assertEquals(0.2679491, actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_Minus15Degree_CorrectValue(){
		double actual = degreeToTangent(-15);
		
		assertEquals(-0.2679491, actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_30Degree_CorrectValue(){
		double actual = degreeToTangent(30);
		
		assertEquals(1.0/ Math.sqrt(3), actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_Minus30Degree_CorrectValue(){
		double actual = degreeToTangent(-30);
		
		assertEquals(-1.0/ Math.sqrt(3), actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_45Degree_CorrectValue(){
		double actual = degreeToTangent(45);
		
		assertEquals(1, actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_Minus45Degree_CorrectValue(){
		double actual = degreeToTangent(-45);
		
		assertEquals(-1, actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_60Degree_CorrectValue(){
		double actual = degreeToTangent(60);
		
		assertEquals(Math.sqrt(3), actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_Minus60Degree_CorrectValue(){
		double actual = degreeToTangent(-60);
		
		assertEquals(-Math.sqrt(3), actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_240Degree_TangentFor60(){
		double actual = degreeToTangent(240);
		
		assertEquals(Math.sqrt(3), actual, Project.ACCURACY);
	}
	
	@Test
	public void DegreeToTangent_120Degree_TangentForMinus60(){
		double actual = degreeToTangent(120);
		
		assertEquals(-Math.sqrt(3), actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToSinus_TangentZero_SinusZero() {
		double actual = tangentToSinus(0);
		
		assertEquals(0, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToSinus_TangentInfinite_SinusOne() {
		double actual = tangentToSinus(Double.POSITIVE_INFINITY);
		
		assertEquals(1, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToSinus_TangentFor15_SinusFor15() {
		double actual = tangentToSinus(degreeToTangent(15));
		
		assertEquals(0.258819045, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToSinus_TangentForMinus15_SinusForMinus15() {
		double actual = tangentToSinus(degreeToTangent(-15));
		
		assertEquals(-0.258819045, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToSinus_TangentFor30_SinusFor30() {
		double actual = tangentToSinus(degreeToTangent(30));
		
		assertEquals(0.5, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToSinus_TangentForMinus30_SinusForMinus30() {
		double actual = tangentToSinus(degreeToTangent(-30));
		
		assertEquals(-0.5, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToSinus_TangentFor45_SinusFor45() {
		double actual = tangentToSinus(degreeToTangent(45));
		
		assertEquals(Math.sqrt(2)/2, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToSinus_TangentForMinus45_SinusForMinus45() {
		double actual = tangentToSinus(degreeToTangent(-45));
		
		assertEquals(-Math.sqrt(2)/2, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToSinus_TangentFor60_SinusFor60() {
		double actual = tangentToSinus(degreeToTangent(60));
		
		assertEquals(Math.sqrt(3)/2, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToSinus_TangentForMinus60_SinusForMinus60() {
		double actual = tangentToSinus(degreeToTangent(-60));
		
		assertEquals(-Math.sqrt(3)/2, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToCosinus_TangentZero_CosinusOne() {
		double actual = tangentToCosinus(0);
		
		assertEquals(1, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToCosinus_TangentInfinite_CosinusZero() {
		double actual = tangentToCosinus(Double.POSITIVE_INFINITY);
		
		assertEquals(0, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToCosinus_TangentFor15_CosinusFor15() {
		double actual = tangentToCosinus(degreeToTangent(15));
		
		assertEquals(0.96592583, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToCosinus_TangentForMinus15_CosinusForMinus15() {
		double actual = tangentToCosinus(degreeToTangent(-15));
		
		assertEquals(0.96592583, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToCosinus_TangentFor30_CosinusFor30() {
		double actual = tangentToCosinus(degreeToTangent(30));
		
		assertEquals(Math.sqrt(3)/2, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToCosinus_TangentForMinus30_CosinusForMinus30() {
		double actual = tangentToCosinus(degreeToTangent(-30));
		
		assertEquals(Math.sqrt(3)/2, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToCosinus_TangentFor45_CosinusFor45() {
		double actual = tangentToCosinus(degreeToTangent(45));
		
		assertEquals(Math.sqrt(2)/2, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToCosinus_TangentForMinus45_CosinusForMinus45() {
		double actual = tangentToCosinus(degreeToTangent(-45));
		
		assertEquals(Math.sqrt(2)/2, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToCosinus_TangentFor60_CosinusFor60() {
		double actual = tangentToCosinus(degreeToTangent(60));
		
		assertEquals(0.5, actual, Project.ACCURACY);
	}
	
	@Test
	public void TangentToCosinus_TangentForMinus60_CosinusForMinus60() {
		double actual = tangentToCosinus(degreeToTangent(-60));
		
		assertEquals(0.5, actual, Project.ACCURACY);
	}
}
