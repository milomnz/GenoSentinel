from django.db import models
from .Gene import Gene


class GeneticVariant(models.Model):
    
    
    id = models.BigAutoField(primary_key=True)
    chromosome = models.CharField(max_length=1000)

    """FK for Gene"""

    gene = models.ForeignKey(
        Gene, 
        on_delete=models.CASCADE,
        db_column='geneId'
    )
    position = models.BigIntegerField()
    reference_base = models.CharField(max_length=10, db_column='referenceBase')
    alternate_base = models.CharField(max_length=10, db_column='alternateBase')
    impact = models.CharField(max_length=50)
    
    class Meta:
        db_table = 'geneticvariant'
        managed = False
    
    def __str__(self):
        return f"Variant {self.id} - {self.chromosome}:{self.position}"