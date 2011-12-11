package com.Khorn.TerrainControl.BiomeManager.Layers;

import net.minecraft.server.*;

public class LayerBiomeInBiome extends Layer
{
    public BiomeBase biome = BiomeBase.MUSHROOM_ISLAND;
    public int chance = 10;
    public boolean inOcean = false;

    public boolean[] BiomeIsles = new boolean[128];


    public LayerBiomeInBiome(long paramLong, Layer paramGenLayer)
    {
        super(paramLong);
        this.a = paramGenLayer;
        for (int i = 0; i < BiomeIsles.length; i++)
            BiomeIsles[i] = false;

    }

    @SuppressWarnings({"PointlessArithmeticExpression"})
    @Override
    public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        int i = paramInt1 - 1;
        int j = paramInt2 - 1;
        int k = paramInt3 + 2;
        int m = paramInt4 + 2;
        int[] arrayOfInt1 = this.a.a(i, j, k, m);

        int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);

        for (int n = 0; n < paramInt4; n++)
        {
            for (int i1 = 0; i1 < paramInt3; i1++)
            {
                a(i1 + paramInt1, n + paramInt2);
                int currentPiece = arrayOfInt1[(i1 + 1 + (n + 1) * k)];

                boolean spawn = false;
                if (inOcean)
                {
                    int i2 = arrayOfInt1[(i1 + 0 + (n + 0) * k)] & LandBit;
                    int i3 = arrayOfInt1[(i1 + 2 + (n + 0) * k)] & LandBit;
                    int i4 = arrayOfInt1[(i1 + 0 + (n + 2) * k)] & LandBit;
                    int i5 = arrayOfInt1[(i1 + 2 + (n + 2) * k)] & LandBit;


                    if (((currentPiece & LandBit) == 0) && (i2 == 0) && (i3 == 0) && (i4 == 0) && (i5 == 0) && a(chance) == 0)
                    {
                        currentPiece = (currentPiece & IceBit) | (currentPiece & RiverBits) | LandBit | biome.F | IslandBit;
                        spawn = true;
                    }
                }
                if (!spawn)
                {
                    int i2 = arrayOfInt1[(i1 + 0 + (n + 0) * k)] & BiomeBits;
                    int i3 = arrayOfInt1[(i1 + 2 + (n + 0) * k)] & BiomeBits;
                    int i4 = arrayOfInt1[(i1 + 0 + (n + 2) * k)] & BiomeBits;
                    int i5 = arrayOfInt1[(i1 + 2 + (n + 2) * k)] & BiomeBits;


                    if (BiomeIsles[(currentPiece & BiomeBits)] && BiomeIsles[i2] && BiomeIsles[i3] && BiomeIsles[i4] && BiomeIsles[i5] && a(chance) == 0)
                        currentPiece = (currentPiece & LandBit) | (currentPiece & IceBit) | (currentPiece & RiverBits) | biome.F | IslandBit;

                }

                arrayOfInt2[(i1 + n * paramInt3)] = currentPiece;
            }
        }
        return arrayOfInt2;
    }
}
