from django.db import models



class PatientVariantReport(models.Model):
    id = models.AutoField(primary_key=True)
    patient_id = models.BigIntegerField(db_column='patientId')
    variant = models.ForeignKey('GeneticVariant',
                                 on_delete=models.CASCADE,
                                 db_column='variantId')
    detection_date = models.DateField(db_column='detectionDate')
    allele_frequency = models.DecimalField(max_digits=5, decimal_places=4, db_column='alleleFrequency')
    class Meta:
        db_table = 'patientvariantreport'
        managed = False