from django.db import models

class Gene(models.Model):
    id = models.BigAutoField(primary_key=True)
    symbol= models.CharField(max_length=20, unique=True)
    fullName = models.CharField(max_length=255)
    functionSummary = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        db_table = 'gene'
        managed = False