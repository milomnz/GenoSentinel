from django.db import models

class Gene(models.Model):
    id = models.BigAutoField(primary_key=True)
    symbol = models.CharField(max_length=20, unique=True)
    full_name = models.CharField(max_length=255, db_column='fullName')
    function_summary = models.CharField(max_length=255, blank=True, null=True, db_column='functionSummary')

    class Meta:
        db_table = 'gene'
        managed = False