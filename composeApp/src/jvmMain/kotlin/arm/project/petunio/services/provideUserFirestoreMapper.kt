package arm.project.petunio.services

import arm.project.petunio.repositories.UserFirestoreImplement

actual fun provideUserFirestoreMapper(): UserFirestoreMapper = UserFirestoreImplement()