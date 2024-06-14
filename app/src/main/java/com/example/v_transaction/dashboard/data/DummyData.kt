import com.example.v_transaction.R
import com.example.v_transaction.database.entity.AccountHolder

object DummyData {
    fun getDummyAccountHolders(): List<AccountHolder> {
        return listOf(
            AccountHolder(
                1,
                "Ireoluwakitan Sanusi",
                R.drawable.ic_user,
                "1234-567-890",
                1200.50
            ),
            AccountHolder(
                2,
                "Oluwaseyi Sanusi",
                R.drawable.ic_user2  ,
                "234-567-8901",
                1500.00
            ),
            AccountHolder(
                3,
                "Oreoluwakitan Sansusi",
                R.drawable.ic_user3,
                "345-678-9012",
                1800.75
            ),
            AccountHolder(
                4,
                "Araireoluwa Sanusi",
                R.drawable.ic_user4,
                "456-789-0123",
                2100.30
            ),
            AccountHolder(
                5,
                "Eva Davis",
                R.drawable.ic_user,
                "567-890-1234",
                2400.45
            ),
            AccountHolder(
                6,
                "Frank Wike",
                R.drawable.ic_user2,
                "678-9012-345",
                2700.60
            ),
            AccountHolder(
                7,
                "Grace Miller",
                R.drawable.ic_user3,
                "7890-123-456",
                3000.85
            ),
            AccountHolder(
                8,
                "Henry Mercy",
                R.drawable.ic_user4,
                "890-123-4567",
                3300.90
            ),
            AccountHolder(
                9,
                "Ivy Ireoluwa",
                R.drawable.ic_user,
                "901-234-5678",
                3600.00
            ),
            AccountHolder(
                10,
                "Jackson Andrew",
                R.drawable.ic_user2,
                "0123-456-789",
                3900.10
            )
        )
    }

}